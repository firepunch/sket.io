import * as types from './ActionTypes';
import fetch from 'isomorphic-fetch';

/* INDEX */

// login
export function handleLogin(social, user) {

    // switch (social) {
    //     case 'facebook':
    //         url = "http://www.facebook.com/dialog/oauth?client_id=741189302727195&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2F"
    //         break;
    //     default:
    //
    //     case 'google':
    //         url = "https://accounts.google.com/o/oauth2/auth?client_id=755801497962-25e8cmnp81pcld5r8mfsvmetus9qnnv4.apps.googleusercontent.com&redirect_uri=http://localhost:8080/&scope=https://www.googleapis.com/auth/plus.login&response_type=code"
    //         break;
    // }

    return dispatch => {
        fetch(`/signin/${social}/`, user)
        .then(res => res.json())
        .then(json => dispatch(receiveUserData(json)))  // 전달 받은 user data를 dispatch
        .catch(error => dispatch(failReceiveUserData()))    // 오류 catch
    }
}

export function requestLogin() {
    return {
        type: types.LOGIN_REQUEST
    }
}


export function failReceiveUserData() {
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
