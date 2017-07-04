import * as actions from '../actions'

const socketMiddleware = (() => {
    var socket = null;

    const onOpen = (ws, store) => evt => {
        // Send a handshake, or authenticate with remote end
        // Tell the store we're connected
        store.dispatch( actions.socketConnected() );
        store.dispatch( actions.sendUserInfo(store.getState().login.user) );
    }

    const onClose = (ws, store) => evt => {
        //Tell the store we've disconnected
        store.dispatch( actions.socketDisconneted() );
        alert('소켓을 닫습니다')
    }

    const onMessage = (ws, store) => evt => {
        // Parse the JSON message received on the websocket
        var msg = JSON.parse(evt.data);

        console.log(msg);

        // Server to Client
        switch(msg.type) {
            case "USER_LIST":   // 접속 유저 목록
                store.dispatch( actions.getUserList(msg.data.userList) );
                break;

            case "ROOM_LIST":   // 현재 있는 방 목록
                store.dispatch( actions.getRoomList(msg.data.roomList) );
                break;

            case "SHOW_RANK":   // 랭킹을 보여줌
                store.dispatch( actions.getRanking(msg.data) );
                break;

            case "NO_ENTER_ROOM":
                alert("방에 들어갈 수 없습니다!!!");
                break;

            case "ROOM_INFO":   // 현재 있는 방에 대한 정보
                store.dispatch( actions.enterRoom(msg.data) );
                break;

            case "REMOVE_ROOM":
                store.dispatch( actions.removeRoom() );
                break;

            case "PLAYER_READY":    // 게임 레디
                if (msg.data.id === store.getState().login.user.id) {    // 자신의 준비 상태가 바뀌었을 경우
                    store.dispatch( actions.changeMyReady(msg.data.ready) );
                } else {    // 다른 사람의 상태가 바뀌었을 경우
                    let playerList = store.getState().game.roomInfo.playerList;

                    for (let i in playerList) {
                        if (msg.data.id === playerList[i].id) {
                            store.dispatch( actions.changeOtherReady(msg.data.ready, i) );
                        }
                    }
                }

                break;

            case "NO_READY_ALL_PLAYER": // 모든 플레이어가 준비를 하지 않았음
                alert("모든 인원이 준비해야 합니다.")
                break;

            case "GAME_START":
                store.dispatch( actions.startGame() );
                break;

            case "SET_EXAMINER": // 게임을 시작했을 때 랜덤 출제자 선정 및 퀴즈 요청
                store.dispatch( actions.setExaminer(msg.data.id) );

                if (store.getState().login.user.id === msg.data.id) {
                    store.dispatch( actions.requestQuiz(store.getState().game.roomInfo.roomId, msg.data.id) );
                }

                break;

            case "RANDOM_QUIZ":
                // 퀴즈를 받는 것은 문제 출제자 뿐임
                // 그렇기 때문에 출제자가 서버에게 START_QUIZ 요청을 해야 함
                store.dispatch( actions.getQuiz(msg.data.id, msg.data.quiz) );
                store.dispatch( actions.requestQuizStart(store.getState().game.roomInfo.roomId) );
                break;

            case "START_QUIZ":
                // if (msg.data.gameEnd || msg.data.round > store.getState().game.roomInfo.numRound) { // 게임이 종료되었을 때
                //     store.dispatch( actions.endGame() );
                // }
                store.dispatch( actions.quizStart(msg.data.round, msg.data.gameEnd));
                break;

            case "CANVAS_DATA":
                store.dispatch( actions.getCanvasData(msg.data.clickX, msg.data.clickY, msg.data.mouse) );
                break;

            case "CHAT_DATA":
                if (msg.data.correct) {
                    // 정답을 맞췄을 때
                    let playerList = store.getState().game.roomInfo.playerList;
                    let playerId   = store.getState().login.user.id;

                    for (let i in playerList) {
                        if (msg.data.userId === playerList[i].id) {
                            store.dispatch( actions.otherCorrectAnswer(msg.data.score, i) );
                        } else if (msg.data.userId === playerId) {
                            store.dispatch( actions.correctAnswer(msg.data.score) );
                        }
                    }
                }
                store.dispatch( actions.chatReceive(msg.data) );
                break;

            case "GAME_TIMEOUT":
                store.dispatch( actions.timeout(msg.data.score) );
                break;

            case "GAME_END":
                // store.dispatch( actions.gameEnd() );
                store.dispatch( actions.endGame(msg.data.ranking) );
                break;

            default:
                console.log("Received unknown message type: '" + msg.type + "'");
                break;
        }
    }

    const onError = (ws, store) => evt => {
        alert('오류 발생')
        console.log('error');
    }

    return store => next => action => {

        // 로그인 되어있지 않았을 때 (isLoggedIn == false)
        // 로그인을 하고 난 후에 소켓을 연결해야 함
        if (!store.getState().login.isLoggedIn) {
            return next(action);
        }

        // Clinet To Server
        switch (action.type) {
            // 소켓 연결 요청
            case 'CONNECT_SOCKET':
                //Start a new connection to the server
                if(socket != null) {
                    socket.close();
                }

                //Send an action that shows a "connecting..." status for now
                store.dispatch( actions.requestSocket() );

                //Attempt to connect (we could send a 'failed' action on error)
                socket = new WebSocket(action.url);

                socket.onopen = onOpen(socket, store);
                socket.onclose = onClose(socket, store);
                socket.onmessage = onMessage(socket, store);
                socket.onerror = onError(socket, store);

                break;

            //The user wants us to disconnect
            case 'DISCONNECT_SOCKET':
                if(socket != null) {
                  socket.close();
                }
                socket = null;

                //Set our state to disconnected
                store.dispatch( actions.disconnected() );

                break;

            //Send the 'SEND_MESSAGE' action down the websocket to the server
            case 'SEND_MESSAGE':
                socket.send(JSON.stringify({
                    type: action.msg_type,
                    data: action.data
                }), () => store.dispatch( actions.sendMessageFinish) );   // 메시지 전송

                break;

            //This action is irrelevant to us, pass it on to the next middleware
            // 별거 없으면 미들웨어에서 그냥 무시
            default:
                return next(action);
        }
    }
})();

export default socketMiddleware;
