import * as types from '../actions/ActionTypes';

const initialState = {
    isReady: false
}

export default function game(state=initialState, action) {
    switch (action.type) {
        case types.GET_READY:

            return {
                ...state,
                isReady: !state.isReady
            }

        default:
            return state;
    }
}
