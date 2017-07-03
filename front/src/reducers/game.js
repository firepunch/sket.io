import * as types from '../actions/ActionTypes';

const initialState = {
    isReady: false,     // 유저가 준비를 하였는지(isMaster가 true라면 이 변수는 의미 없음)
    isGame: false,      // 게임 입장
    isPlay: false,      // 게임 시작 여부
    isQuiz: false,      // 퀴즈가 시작했는지. 한 개의 퀴즈가 끝날 때마다 false가 되어야 함
    isTimer: false,

    quiz: {},           // 받은 퀴즈
    roundInfo: {},      // 라운드 정보
    examinerId: '',     // 문제 출제자 아이디
    score: 0,           // 자기 자신의  점수

    roomInfo: {},        // 방 정보 객체
    canvas: {},
    chat: {}
}

/*
1.  방 입장 : ROOM_INFO 수신
2.  ROOM_INFO 의 데이터를 이용하여 방장 설정 및 UI 구성
3.  준비 버튼을 눌렀을 때 준비 / 대기 토글
4.  PLAYER_READY 을 받아 어떤 유저가 준비를 했는지 확인 및 UI 변경
5.  게임 시작 (모든 플레이어가 준비를 마치지 않았을 시 NO_READY_ALL_PLAYER 메시지 수신)
6.  정상적으로 게임이 시작되었을 때 GAME_START 메시지를 받아 확인 및 UI 변경
7.  RANDOM_EXAMINER 를 통해 첫 문제 출제자를 랜덤으로 선정함
8.  RANDOM_QUIZ 요청을 출제자 ID와 함께 서버에 보내 퀴즈를 받아옴
9.  CANVAS_DATA 로 캔버스 스케치 데이터를 실시간으로 전송 - CHAT_DATA 를 이용하여 채팅 및 정답 검사
10. CHAT_DATA의 correct 변수를 통해 정답 여부 확인 및 결과 처리(점수 변경 등) - 진행 중
11. GAME_TIMEOUT 이 되면 모든 유저가 공통적으로 감점을 받음(score)
12. 게임이 종료되면 GAME_END 전송(roomId, scoreInfo: 유저 아이디, 레벨, 경험치 등)
13. 레벨업 모달 띄어주기


퀴즈 끝나고 초기화해줄 것이 많음
quiz, examinerId, isModal(GameArea), canvas 등
*/

export default function game(state=initialState, action) {

    switch (action.type) {

        case types.ROOM_INFO:
            return {
                ...state,
                isGame: true,
                roomInfo: action.roomInfo
            }

        case types.EXIT_ROOM:
            return {
                ...state
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

        case types.GAME_START:
            return {
                ...state,
                isPlay: true
            }

        case types.SET_EXAMINER:
            return {
                ...state,
                examinerId: action.userId
            }

        case types.GET_QUIZ:
            return {
                ...state,
                quiz: action.quiz
            }

        case types.START_QUIZ:
            return {
                ...state,
                roundInfo: action.roundInfo,
                isQuiz: true
            }

        case types.START_TIMER:
            return {
                ...state,
                isTimer: true
            }

        case types.CANVAS_DATA:
            return {
                ...state,
                canvas: action.data
            }

        case types.CHAT_DATA:
            return {
                ...state,
                chat: action.chat
            }

        case types.OTHER_CORRECT_ANSWER:
            return {
                ...state,
                roomInfo: {
                    ...state.roomInfo,
                    playerList: [
                        ...state.roomInfo.playerList.slice(0, action.userIndex),
                        Object.assign({}, state.roomInfo.playerList[action.userIndex], {
                            score: state.roomInfo.playerList[action.userIndex].score + action.score
                        }),
                        ...state.roomInfo.playerList.slice(action.userIndex + 1)
                    ]
                },
                isQuiz: false,
                isTimer: false,
                quiz: '',
                examinerId: ''
            }

        case types.CORRECT_ANSWER:
            return {
                ...state,
                score: state.score + action.score
            }

        default:
            return state;
    }
}
