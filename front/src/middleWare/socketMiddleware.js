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
    }

    const onMessage = (ws, store) => evt => {
        // Parse the JSON message received on the websocket
        var msg = JSON.parse(evt.data);

        console.log(msg);

        // Server to Client
        switch(msg.type) {
            case "USER_LIST":
                store.dispatch( actions.getUserList(msg.data.userList) );
                break;

            case "ROOM_LIST":
                store.dispatch( actions.getRoomList(msg.data.roomList) );
                break;

            case "SHOW_RANK":
                store.dispatch( actions.getRanking(msg.data) );
                break;

            case "ROOM_INFO":
                // if (msg.data.roomMaster === store.getState().login.user.id) {
                //     store.dispatch( actions.setMaster() );
                // }
                store.dispatch( actions.enterRoom(msg.data) );
                break;

            case "PLAYER_READY":
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

            case "READY_ALL_PLAYER":
                alert("모든 인원이 준비해야 합니다.")
                break;

            default:
                console.log("Received unknown message type: '" + msg.type + "'");
                break;
        }
    }

    const onError = (ws, store) => evt => {
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
