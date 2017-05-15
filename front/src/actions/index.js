import * as types from './ActionTypes';
import fetch from 'isomorphic-fetch';

/* INDEX */

// login
export function handleLogin(social) {
    return dispatch => {
        dispatch(requestLogin())
        fetch(`/signin/${social}/`)
        .then(res => res.json())
        .then(json => dispatch(receiveUserData(json)))  // 전달 받은 user data를 dispatch
        .catch(error => dispatch(failReceiveUserData()))    // 오류 catch
    }
}

function requestLogin() {
    return {
        type: types.LOGIN_REQUEST
    }
}

function receiveUserData(user) {
    return {
        type: types.LOGIN_SUCCESS,
        user
    }
}

function failReceiveUserData() {
    return {
        type: types.LOGIN_FAILURE
    }
}

// function
export function createRoom() {
    return {
        type: types.CREATE_ROOM
    }
}

export function changeModalUsage(usage) {
    return {
        type: types.CHANGE_MODAL_USAGE,
        usage
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

// export function quick_start() {
//     return {
//         type: types.QUICK_START
//     }
// }
