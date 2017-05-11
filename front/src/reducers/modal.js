import * as types from '../actions/ActionTypes';

const initialState = {
    usage: 'LOGIN'
}

export default function modal(state=initialState, action) {
    switch (action.type) {
        case types.CHANGE_MODAL_USAGE:
            return {
                ...state,
                usage: action.usage
            }

        default:
            return state;
    }
}
