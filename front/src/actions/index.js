import * as types from './ActionTypes';


/* INDEX */

// login
export function googleLogin() {
    return {
        type: types.GOOGLE_LOGIN
    }
}

export function facebookLogin() {
    return {
        type: types.FACEBOOK_LOGIN
    }
}

export function guestLogin() {
    return {
        type: types.GUEST_LOGIN
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
