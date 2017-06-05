import * as types from '../actions/ActionTypes';

const initialState = {
    isReady: false,
    isGame: false,
    isMaster: false,

    roomInfo: {}
}

export default function game(state=initialState, action) {
    // if (action.msg_type === "GAME_READY") {
    //     return {
    //         ...state,
    //         isReady: !state.isReady
    //     }
    // }

    switch (action.type) {

        case types.ROOM_INFO:
            return {
                ...state,
                isGame: true,
                roomInfo: action.roomInfo
            }

        case types.SET_MASTER:
            return {
                ...state,
                isMaster: true
            }

        case types.MY_READY:
            return {
                ...state,
                isReady: action.ready
            }

        case types.OTHER_READY:
            return {
                ...state,
                roomInfo: {
                    ...state.roomInfo,
                    playerList: [
                        ...state.roomInfo.playerList.slice(0, action.userIndex),
                        Object.assign({}, state.roomInfo.playerList[action.userIndex], {
                            isReady: action.ready
                        }),
                        ...state.roomInfo.playerList.slice(action.userIndex + 1)
                    ]
                }
            }

        default:
            return state;
    }
}
