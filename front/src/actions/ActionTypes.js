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
// export const CREATE_ROOM    = "CREATE_ROOM";
export const QUICK_START = "QUICK_START";
export const SHOW_RANKING = "SHOW_RANKING";
export const ENTER_ROOM = "ENTER_ROOM";

export const GET_USER_LIST = "GET_USER_LIST";
export const GET_ROOM_LIST = "GET_ROOM_LIST";
export const GET_RANKING = "GET_RANKING";


/* GAME */
export const DRAW_PICTURE   = "DRAW_PICTURE";
export const START_TIMER    = "START_TIMER";
export const CHAT_ANSWER    = "CHAT_ANSWER";
export const CLOSE_MODAL    = "CLOSE_MODAL";

export const GET_READY      = "GET_READY";
export const GET_START      = "GET_START";

/* TEST */
export const SWITCH_INDEX   = "SWITCH_INDEX";
