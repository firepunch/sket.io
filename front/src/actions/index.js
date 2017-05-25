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
        // .then(json => {dispatch( receiveUserData(json) ))  // 전달 받은 user data를 dispatch
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
