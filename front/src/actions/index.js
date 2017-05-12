import * as types from './ActionTypes';


/* INDEX */

// login
export function googleLogin() {
    return {
        type: types.INCREMENT
    }
}

export function facebookLogin() {
    return {
        type: types.DECREMENT
    }
}

// function
export function createRoom() {
    return {
        type: types.CREATE_ROOM
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
