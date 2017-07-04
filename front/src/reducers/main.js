import * as types from '../actions/ActionTypes';

const initialState = {
    fetchingConnect: false,  // 소켓 연결 중
    isConnecting: false,     // 소켓 연결 여부
    isSocketFetching: false,  // 소켓 request / response 중
    isShowRanking: false,    // 랭킹 보여주는지

    userList: [],
    roomList: {},
    ranking: {}
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

        case types.GET_USER_LIST:
            return {
                ...state,
                userList: action.userList
            }

        case types.GET_ROOM_LIST:
            return {
                ...state,
                roomList: action.roomList
            }

        case types.GET_RANKING:
            return {
                ...state,
                ranking: action.ranking
            }

        case types.SHOW_RANK:
            return {
                ...state,
                isShowRanking: !state.isShowRanking
            }

        default:
            return state;
    }
}
