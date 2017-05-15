import * as types from './ActionTypes';


/* INDEX */

// login
export function handleLogin(social) {
    return dispatch => {
        switch (social) {
            case 'FACEBOOK':
                return dispatch(facebookLogin());

            case 'GOOGLE':
                return dispatch(googleLogin());

            case 'GUEST':
                return dispatch(guestLogin());
        }
    }
}

function facebookLogin() {
    return {
        type: types.FACEBOOK_LOGIN,
        promise: { method: 'post', url: '/singin/facebook', data: ''}
    }
}

function googleLogin() {
    return {
        type: types.GOOGLE_LOGIN,
        promise: { method: 'post', url: '/singin/google', data: ''}
    }
}

function guestLogin() {
    return {
        type: types.GUEST_LOGIN,
        promise: { method: 'post', url: '/guest.do', data: ''}
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
