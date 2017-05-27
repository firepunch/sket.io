import * as types from './ActionTypes';
import fetch from 'isomorphic-fetch';

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

export function createRoom(roomInfo) {
    return {
        type: types.SEND_MESSAGE,
        msg_type: "CREATE_ROOM",
        data: roomInfo
    }
}



/* GAME */

export function getReady() {
    return {
        type: types.GET_READY,
        // isReady     // boolean
    }
}


/* TEST */
export function switch_index() {
    return {
        type: types.SWITCH_INDEX
    }
}
