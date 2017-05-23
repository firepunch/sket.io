import * as types from '../actions/ActionTypes';

const initialState = {
    fetchingConnect: false,  // 소켓 연결 중
    isConnecting: false     // 소켓 연결 여부
}

export default function main(state=initialState, action) {
    switch (action.type) {
        case types.REQUEST_SOCKET:
            return {
                ...state,
                fetchingConnect: true
            }

        case types.SOCKET_CONNECTED:
            return {
                ...state,
                fetchingConnect: false,
                isConnecting: true
            };

        case types.SOCKET_DISCONNETED:
            return {
                ...state,
                isConnecting: true
            }

        case types.CHANGE_MODAL_USAGE:
            return {
                ...state,
                usage: action.usage
            }

        default:
            return state;
    }
}
