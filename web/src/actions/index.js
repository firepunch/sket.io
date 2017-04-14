import * as types from './ActionTypes';


export function google_login() {
    return {
        type: types.INCREMENT
    }
}

export function facebook_login() {
    return {
        type: types.DECREMENT
    }
}

export function create_room() {
    return {
        type: types.CREATE_ROOM
    }
}

// export function quick_start() {
//     return {
//         type: types.QUICK_START
//     }
// }
