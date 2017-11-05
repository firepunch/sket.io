import * as types from './ActionTypes';
import fetch from 'isomorphic-fetch';

// const url = 'ws://10.156.145.162:8080/websocket'
const url = 'ws://localhost:8080/websocket'

/* INDEX */

// login
export function handleLogin(social, user) {
    return async dispatch => {
        await fetch(`/signin/${social}/`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json; charset=utf-8',
            },
            body: JSON.stringify({
                user
            })
        })
        .then(res => res.json())
        .then(json => {
            dispatch( receiveUserData(json) );
            dispatch( connectSocket() );    // 소켓 연결 요청
        })
        .catch(error => dispatch( failReceiveUserData() ))    // 오류 catch

    }
}

export function handleGuestLogin() {
    return dispatch => {
        dispatch( requestLogin() );
        dispatch( handleLogin('guest', '') );
    }
}

export function handleLogout() {
    return async dispatch => {
        dispatch( requestLogout() );

        await fetch('/singout')
        .then(res => res.json())
        .then(json => {
            dispatch( disconnectSocket() );
            dispatch( successLogout() );
        })
        .catch(error => dispatch( failLogout() ))
    }
}

export function requestLogin() {
    return {
        type: types.LOGIN_REQUEST
    }
}

function failReceiveUserData() {
    return {
        type: types.LOGIN_FAILURE
    }
}

function receiveUserData(user) {
    return {
        type: types.LOGIN_SUCCESS,
        user
    }
}

function requestLogout() {
    return {
        type: types.LOGOUT_REQUEST
    }
}

function successLogout() {
    return {
        type: types.LOGOUT_SUCCESS
    }
}

function failLogout() {
    return {
        type: types.LOGOUT_FAILURE
    }
}


// WebSocket
export function requestSocket() {
    return {
        type: types.REQUEST_SOCKET
    }
}

function connectSocket() {
    return {
        type: types.CONNECT_SOCKET,
        url
    }
}

function disconnectSocket() {
    return {
        type: types.DISCONNECT_SOCKET
    }
}

export function socketConnected() {
    return {
        type: types.SOCKET_CONNECTED
    }
}

export function socketDisconneted() {
    return {
        type: types.SOCKET_DISCONNETED
    }
}

export function sendMessageFinish() {
    return {
        type: types.SEND_MESSAGE_FINISH
    }
}



/* Websocket Request */

export function sendUserInfo(user) {
    return {
        type: types.SEND_MESSAGE,
        msg_type: "CREATE_PLAYER",
        data: user
    }
}

// 방 생성
export function createRoom(roomInfo) {
    return {
        type: types.SEND_MESSAGE,
        msg_type: "CREATE_ROOM",
        data: roomInfo
    }
}

// 빠른 시작
export function quickStart() {
    return {
        type: types.SEND_MESSAGE,
        msg_type: "QUICK_START",
        data: {}
    }
}

// 랭킹
export function showRanking(userId) {
    return dispatch => {
        dispatch( requestRanking(userId) );
        dispatch( handleRanking() )
    }
}

// 랭킹 요청
function requestRanking(userId) {
    return {
        type: types.SEND_MESSAGE,
        msg_type: "SHOW_RANK",
        data: { id: userId }
    }
}

// 랭킹 관련 state 변수 변경
function handleRanking() {
    return {
        type: types.SHOW_RANK
    }
}

export function enterRoom(roomInfo) {
    return {
        type: types.ROOM_INFO,
        roomInfo
    }
}

export function getRoomInfo(roomId, userId) {
    return {
        type: types.SEND_MESSAGE,
        msg_type: "ENTER_ROOM",
        data: { roomId, userId }
    }
}

export function getCreatingRoomId(roomId) {
    return {
        type: types.GET_ROOM_ID,
        roomId
    }
}


/* Websocket Response */

export function messageReceived() {

}

// function
export function getUserList(userList) {
    return {
        type: types.GET_USER_LIST,
        userList
    }
}

export function getRoomList(roomList) {
    return {
        type: types.GET_ROOM_LIST,
        roomList
    }
}

export function getRanking(ranking) {
    return {
        type: types.GET_RANKING,
        ranking
    }
}



/* GAME */

export function requestExitRoom(roomId, userId) {
    return {
        type: types.SEND_MESSAGE,
        msg_type: "EXIT_ROOM",
        data: { roomId, userId  }
    }
}

export function exitRoom() {
    return {
        type: types.EXIT_ROOM
    }
}

export function removeRoom() {
    return {
        type: types.REMOVE_ROOM
    }
}

export function getReady(roomId, userId, isReady) {
    return {
        type: types.SEND_MESSAGE,
        msg_type: "GAME_READY",
        data: { roomId, userId, isReady }   // isReady 는 현재 상태의 반대값으로 전달됨
    }
}

export function changeMyReady(ready) {
    return {
        type: types.MY_READY,
        ready
    }
}

export function changeOtherReady(ready, userIndex) {
    return {
        type: types.OTHER_READY,
        ready,
        userIndex
    }
}

export function requestStartGame(roomId, userId) {
    return {
        type: types.SEND_MESSAGE,
        msg_type: "GAME_START",
        data: { roomId, userId }
    }
}

export function startGame() {
    return {
        type: types.GAME_START
    }
}

export function setExaminer(userId) {
    return {
        type: types.SET_EXAMINER,
        userId
    }
}

export function requestQuiz(roomId, userId) {
    return {
        type: types.SEND_MESSAGE,
        msg_type: "RANDOM_QUIZ",
        data: { roomId, userId }
    }
}

export function getQuiz(userId, quiz) {
    return {
        type: types.GET_QUIZ,
        quiz: { userId, quiz }
    }
}

export function requestQuizStart(roomId) {
    return {
        type: types.SEND_MESSAGE,
        msg_type: "START_QUIZ",
        data: {
            roomId: roomId
        }
    }
}

export function quizStart(roundInfo) {
    return {
        type: types.START_QUIZ,
        roundInfo
    }
}

export function startTimer() {
    return {
        type: types.START_TIMER
    }
}

export function drawCanvas(data) {
    return {
        type: types.SEND_MESSAGE,
        msg_type: "CANVAS_DATA",
        data
    }
}

export function getCanvasData(clickX, clickY, mouse) {
    return {
        type: types.CANVAS_DATA,
        data: { clickX, clickY, mouse }
    }
}

export function chatSend(roomId, userId, restTime, msg) {
    return {
        type: types.SEND_MESSAGE,
        msg_type: "CHAT_DATA",
        data: {
            roomId,
            userId,
            restTime,
            msg
        }
    }
}

export function otherCorrectAnswer(score, userIndex) {
    return {
        type: types.OTHER_CORRECT_ANSWER,
        score,
        userIndex
    }
}

export function correctAnswer(score) {
    return {
        type: types.CORRECT_ANSWER,
        score
    }
}

export function chatReceive(msg) {
    return {
        type: types.CHAT_DATA,
        chat: msg
    }
}

export function timeout(score) {
    return {
        type: types.GAME_TIMEOUT,
        score
    }
}

export function noticeTimeout(roomId) {
    return {
        type: types.SEND_MESSAGE,
        msg_type: types.GAME_TIMEOUT,
        data: {
            roomId
        }
    }
}

export function quizModal(bool) {
    return {
        type: types.QUIZ_MODAL,
        bool
    }
}

export function quizResultModal(bool) {
    return {
        type: types.QUIZ_RESULT_MODAL,
        bool
    }
}

export function timeoutModal() {
    return {
        type: types.TIMEOUT_MODAL
    }
}

export function endGame(ranking) {
    return {
        type: types.GAME_END,
        ranking
    }
}

// export function gameEnd() {
//     return {
//         type: types.
//     }
// }
