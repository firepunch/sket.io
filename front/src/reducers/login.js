import * as types from '../actions/ActionTypes';

const initialState = {

}

export default function login(state=initialState, action) {
    switch (action.type) {
        case types.GOOGLE_LOGIN:

            break;

        case types.FACEBOOK_LOGIN:

            break;

        default:
            return state;
    }
}
