import * as types from '../actions/ActionTypes';

const initialState = {
    isReady: false,
    isGame: false,

    roomInfo: {}
}

export default function game(state=initialState, action) {
    switch (action.type) {
        case types.GET_READY:

            return {
                ...state,
                isReady: !state.isReady
            }

        case types.ENTER_ROOM:
            return {
                ...state,
                roomInfo: action.roomInfo,
                isGame: true
            }

        default:
            return state;
    }
}
