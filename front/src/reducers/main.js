import * as types from '../actions/ActionTypes';

const initialState = {
    // user: {
    //     profileImg: '',
    //     userName: 'GUEST1234',
    //     level: 1,
    //     exp: 1
    // }
}

export default function main(state=initialState, action) {
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
