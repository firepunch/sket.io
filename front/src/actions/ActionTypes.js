/* INDEX */

// login logout
export const LOGIN_REQUEST = "LOGIN_REQUEST";
export const LOGIN_SUCCESS = "LOGIN_SUCCESS";
export const LOGIN_FAILURE = "LOGIN_FAILURE";

export const LOGOUT_REQUEST = "LOGOUT_REQUEST";
export const LOGOUT_SUCCESS = "LOGOUT_SUCCESS";
export const LOGOUT_FAILURE = "LOGOUT_FAILURE";

// WebSocket
export const REQUEST_SOCKET = "REQUEST_SOCKET";         // 소켓 연결 중

export const CONNECT_SOCKET = "CONNECT_SOCKET";         // 소켓 연결
export const DISCONNECT_SOCKET = "DISCONNECT_SOCKET";   // 소켓 종료

export const SOCKET_CONNECTED = "SOCKET_CONNECTED";     // 소켓 연결 알림
export const SOCKET_DISCONNETED = "SOCKET_DISCONNETED"; // 소켓 종료 알림

export const SEND_MESSAGE = "SEND_MESSAGE";             // 소켓 메시지 전송
export const SEND_MESSAGE_FINISH = "SEND_MESSAGE_FINISH";   // 소켓 메시지 전송 완료

// header function
export const SHOW_RANK = "SHOW_RANK";
export const ROOM_INFO = "ROOM_INFO";
export const ENTER_ROOM = "ENTER_ROOM";

export const GET_USER_LIST = "GET_USER_LIST";
export const GET_ROOM_LIST = "GET_ROOM_LIST";
export const GET_RANKING = "GET_RANKING";
export const GET_ROOM_ID = "GET_ROOM_ID";


/* GAME */
export const EXIT_ROOM      = "EXIT_ROOM";
export const REMOVE_ROOM    = "REMOVE_ROOM";

export const MY_READY       = "MY_READY";
export const OTHER_READY    = "OTHER_READY";
export const GAME_START     = "GAME_START";

export const SET_EXAMINER   = "SET_EXAMINER";
export const GET_QUIZ       = "GET_QUIZ";
export const START_QUIZ     = "START_QUIZ";
export const START_TIMER    = "START_TIMER";

export const CANVAS_DATA    = "CANVAS_DATA";
export const CHAT_DATA      = "CHAT_DATA";

export const OTHER_CORRECT_ANSWER = "OTHER_CORRECT_ANSWER";
export const CORRECT_ANSWER = "CORRECT_ANSWER";
export const ADD_SCORE      = "ADD_SCORE";
export const GAME_TIMEOUT   = "GAME_TIMEOUT";

export const QUIZ_MODAL     = "QUIZ_MODAL";
export const QUIZ_RESULT_MODAL = "QUIZ_RESULT_MODAL";
export const TIMEOUT_MODAL  = "TIMEOUT_MODAL";

export const GAME_END       = "GAME_END";
// export const SHOW_RESULT    = 
