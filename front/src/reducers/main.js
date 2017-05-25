import * as types from '../actions/ActionTypes';

const initialState = {
    fetchingConnect: false,  // 소켓 연결 중
    isConnecting: false,     // 소켓 연결 여부
    isSocketFetching: false  // 소켓 request / response 중
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
                isConnecting: false
            }

        case types.SEND_MESSAGE:
            return {
                ...state,
                isSocketFetching: true
            }

        case types.SEND_MESSAGE_FINISH:
            return {
                ...state,
                isSocketFetching: false
            }

        default:
            return state;
    }
}
