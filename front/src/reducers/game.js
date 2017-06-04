import * as types from '../actions/ActionTypes';

const initialState = {
    isReady: false,
    isGame: false,

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

        case types.ENTER_ROOM:
        return {
            ...state,
            roomInfo: action.roomInfo,
            isGame: true
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
