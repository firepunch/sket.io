package sket;

import org.json.JSONArray;
import org.json.JSONObject;
import sket.controllers.GameController;
import sket.controllers.PlayerController;
import sket.controllers.QuizController;
import sket.controllers.RoomController;
import sket.db.DBConnection;
import sket.model.action.PlayerAction;
import sket.model.action.RoomAction;
import sket.model.data.Player;
import sket.model.data.Room;
import sket.model.data.User;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 게임과 연결하는 웹 소켓
 * Created by hojak,firepunch on 2017-04-06.
 */

@ServerEndpoint(value = "/websocket")
public class WebSocket {

    // 현재 웹소켓에 연결되어 있는 session 을 저장하는 ArrayList (HttpSession 과 다름)
    private static Map<String, Session> webSocketSessionMap = new HashMap<>();
    private LinkedList<String> roomMembers;

    private Room targetRoom = null;
    private RoomAction roomAction = null;
    private Player player;
    private Session playerSession;

    // 세션리스트에 접속한 세션 추가, Player 객체 생성, 생성된 룸 정보 보냄
    @OnOpen
    public void onOpen(Session rcvSession) throws IOException, SQLException {
        webSocketSessionMap.put(rcvSession.getId(), rcvSession);

        // session 에 룸 리스트 보냄
        rcvSession.getBasicRemote().sendText(RoomController.getRoomListAsJSON());

        sendMessageToAllSession(getConnectUserListToJSON());

        System.out.println(RoomController.getRoomListAsJSON());
    }

    @OnMessage
    public void onMessage(String message, Session rcvSession) throws IOException, SQLException {
        // 세션리스트에 접속한 세션 추가, Player객체 생성
        // 접속 유저, 생성된 방의 정보 전송

        System.out.println("OnMessage( " + message + " )");
        JSONObject jsonObject = new JSONObject(message);

        switch (jsonObject.getString("type")) {

            // 제일 처음에 플레이어 생성
            case "CREATE_PLAYER":
                player = new Player(
                        jsonObject.getJSONObject("data").getString("id"),
                        jsonObject.getJSONObject("data").getString("nick"),
                        jsonObject.getJSONObject("data").getString("picture"),
                        rcvSession.getId(),
                        jsonObject.getJSONObject("data").getBoolean("isGuest")
                );
                break;

            // 방 생성 했을 때 보내는 JSON
            case "CREATE_ROOM":

                System.out.println("lock : " + jsonObject.getJSONObject("data").getBoolean("lock")
                        + ", " + jsonObject.getJSONObject("data").getString("password"));

                targetRoom = RoomController.createRoom(
                        jsonObject.getJSONObject("data").getString("roomName"),
                        jsonObject.getJSONObject("data").getBoolean("lock"),
                        jsonObject.getJSONObject("data").getString("password"),
                        jsonObject.getJSONObject("data").getString("master"),
                        jsonObject.getJSONObject("data").getInt("userNumLimit"),
                        jsonObject.getJSONObject("data").getInt("limitTime"),
                        jsonObject.getJSONObject("data").getInt("limitRound")
                );

                player.setInRoom(true);
                rcvSession.getBasicRemote().sendText(
                        RoomController.getRoomInfoToJSON(targetRoom)
                );
                sendMessageToAllSession(RoomController.getRoomListAsJSON());
                break;

            // 방 리스트 보내는 JSON
            case "ROOM_LIST":
                rcvSession.getBasicRemote().sendText(RoomController.getRoomListAsJSON());
                break;

            // 방 들어갔을 때 보내는 JSON
            case "ENTER_ROOM":

                targetRoom = RoomAction.findRoomById(jsonObject.getJSONObject("data").getInt("roomId"));

                if (targetRoom.getTotalUserNumber() < targetRoom.getUserMax()) {
                    RoomAction.enterRoom(
                            jsonObject.getJSONObject("data").getInt("roomId"),
                            jsonObject.getJSONObject("data").getString("userId")
                    );

                    roomAction = new RoomAction(targetRoom);
                    player = PlayerAction.getEqualPlayerId(jsonObject.getJSONObject("data").getString("userId"));
                    player.setInRoom(true);

                    sendMessageToRoomMembers(roomAction, RoomController.getRoomInfoToJSON(targetRoom));
                    System.out.println("ENTER_ROOM : " + RoomController.getRoomInfoToJSON(targetRoom));

                } else {
                    rcvSession.getBasicRemote().sendText(
                            RoomController.noEnterRoom(jsonObject.getJSONObject("data").getString("userId"))
                    );
                }

                break;

            // 빠른 시작 시 JSON
            case "QUICK_START":
                targetRoom = RoomAction.getRandomRoom(jsonObject.getJSONObject("data").getString("roomId"));
                roomAction = new RoomAction(targetRoom);

                sendMessageToRoomMembers(roomAction, RoomController.getRoomInfoToJSON(targetRoom));
                break;

            // 플레이어가 방에서 준비했을 때 보내는 JSON. 만약 방장 제외 모두 준비했을 시 방장에거 모두 준비했다고 알림!
            case "GAME_READY":
                targetRoom = RoomAction.findRoomById(jsonObject.getJSONObject("data").getInt("roomId"));
                roomAction = new RoomAction(targetRoom);

                String readyJSON = PlayerController.gameReadyToJSON(
                        jsonObject.getJSONObject("data").getInt("roomId"),
                        jsonObject.getJSONObject("data").getBoolean("isReady"),
                        rcvSession.getId()
                );

                sendMessageToRoomMembers(roomAction, readyJSON);

                break;

            // 방장이 게임 시작 눌렀을 시에 게임 준비 체크
            case "GAME_START":
                targetRoom = RoomAction.findRoomById(jsonObject.getJSONObject("data").getInt("roomId"));
                roomAction = new RoomAction(targetRoom);

                String readyAllPlayerByJSON = PlayerController.checkReadyAllPlayer(targetRoom);
                Session masterSession = webSocketSessionMap.get(targetRoom.getRoomMaster().getSessionID());

                if (readyAllPlayerByJSON != null) {
                    sendMessageToRoomMembers(roomAction, readyAllPlayerByJSON);
                    sendMessageToRoomMembers(
                            roomAction,
                            GameController.randomExaminerToJSON(targetRoom.getRoomId())
                    );
                    targetRoom.setPlayingGame(true);
                    sendMessageToAllSession(RoomController.getRoomListAsJSON());
                    targetRoom.setGameEnd(false);
                } else {
                    masterSession.getBasicRemote().sendText(PlayerController.noReadyAllPlayerJSON(targetRoom));
                }
                break;

            // 랜덤 출제자 선택해서 JSON 보냄
            case "RANDOM_EXAMINER":
                targetRoom = RoomAction.findRoomById(jsonObject.getJSONObject("data").getInt("roomId"));
                roomAction = new RoomAction(targetRoom);

                if (targetRoom != null) {

                }
                break;

            // 랜덤 문제 JSON 보냄
            case "RANDOM_QUIZ":
                targetRoom = RoomAction.findRoomById(jsonObject.getJSONObject("data").getInt("roomId"));
                roomAction = new RoomAction(targetRoom);
                JSONObject quizData = QuizController.sendQuizByJSON(targetRoom,
                        jsonObject.getJSONObject("data").getString("userId"));

                if (targetRoom != null) {
                    Player targetPlayer = PlayerAction.getEqualPlayerId(jsonObject.getJSONObject("data").getString("userId"));
                    playerSession = webSocketSessionMap.get(targetPlayer.getSessionID());
                    System.out.println(String.valueOf(quizData));
                    playerSession.getBasicRemote().sendText(String.valueOf(quizData));
                    playerSession.getBasicRemote().sendText(QuizController.alarmStartQuiz()); //START_QUIZ
                }
                break;

            // 캔바스 데이터 JSON 보냄
            case "CANVAS_DATA":
                targetRoom = RoomAction.findRoomById(jsonObject.getJSONObject("data").getInt("roomId"));
                roomAction = new RoomAction(targetRoom);
                roomMembers = roomAction.getPlayerSessionId();

                if (roomMembers != null) {
                    for (String playerSessionId : roomMembers) {
                        playerSession = webSocketSessionMap.get(playerSessionId);
                        sendMessageToRoomMembers(roomAction, String.valueOf(jsonObject));
                    }
                }
                break;

            // 채팅 JSON
            case "CHAT_DATA":
                targetRoom = RoomAction.findRoomById(jsonObject.getJSONObject("data").getInt("roomId"));
                roomAction = new RoomAction(targetRoom);
                roomMembers = roomAction.getPlayerSessionId();
                String senderId = jsonObject.getJSONObject("data").getString("userId");
                player = PlayerAction.getEqualPlayerId(senderId);

                String msg = jsonObject.getJSONObject("data").getString("msg"); // 정답 비교 시 필요

                java.util.Date date = new java.util.Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

                jsonObject.getJSONObject("data").append("time", sdf.format(date));
                jsonObject.getJSONObject("data").append("userNick", player.getNickname());

                // 정답 확인
                if (msg.equals(targetRoom.getAnswer())) {
                    int restTime = jsonObject.getJSONObject("data").getInt("restTime");
                    int addScore = QuizController.getScore(targetRoom.getTimeLimit(), restTime);
                    QuizController.addScore(jsonObject.getJSONObject("data").getString("userId"), addScore);

                    jsonObject.getJSONObject("data").append("correct", "true");
                    jsonObject.getJSONObject("data").append("score", addScore);

                    if (targetRoom.getCurRound() == targetRoom.getRoundLimit()) {
                        //sendMessageToRoomMembers();
                        break;
                    }
                } else {
                    jsonObject.getJSONObject("data").append("correct", "false");
                }

                sendMessageToRoomMembers(roomAction, String.valueOf(jsonObject));
                break;

            // 타임아웃일 때 전체 점수 감점 JSON
            case "GAME_TIMEOUT":
                targetRoom = RoomAction.findRoomById(jsonObject.getJSONObject("data").getInt("roomId"));
                roomAction = new RoomAction(targetRoom);
                QuizController.minusScore(jsonObject.getJSONObject("data").getInt("roomId"), 10);
                jsonObject.getJSONObject("data").append("score", 10);

                sendMessageToRoomMembers(roomAction, String.valueOf(jsonObject));
                break;

            // 랭킹 JSON
            case "SHOW_RANK":
                DBConnection db = new DBConnection();
                JSONObject rankInfo = db.showRank(jsonObject.getJSONObject("data").getString("id"));
                rcvSession.getBasicRemote().sendText(String.valueOf(rankInfo));
                break;

            // 플레이어가 방 나갔을 때
            case "EXIT_ROOM":
                targetRoom = RoomAction.findRoomById(jsonObject.getJSONObject("data").getInt("roomId"));

                roomAction = new RoomAction(targetRoom);
                player = PlayerAction.getEqualPlayerId(jsonObject.getJSONObject("data").getString("userId"));
                player.setInRoom(false);

                targetRoom.deletePlayer(player.getId());

                sendMessageToRoomMembers(
                        roomAction,
                        RoomController.getRoomInfoToJSON(targetRoom)
                );

                if (targetRoom.getTotalUserNumber() == 0) {
                    Room.getRoomList().remove(targetRoom);
                    sendMessageToAllSession(RoomController.removeRoomByJSON(targetRoom));
                }

                if (player.isMaster()) {
                    targetRoom.setRoomMaster(roomAction.getRandomExaminer());
                }

                System.out.println("방 정보 : " + RoomController.getRoomInfoToJSON(targetRoom));
                sendMessageToRoomMembers(
                        roomAction,
                        RoomController.getRoomInfoToJSON(targetRoom)
                );
                break;

            case "START_QUIZ":
                targetRoom = RoomAction.findRoomById(jsonObject.getJSONObject("data").getInt("roomId"));
                roomAction = new RoomAction(targetRoom);

                sendMessageToRoomMembers(
                        roomAction,
                        RoomController.getRoomStartQuizToJSON(
                                targetRoom.getCurRound(),
                                targetRoom.isGameEnd()
                        )
                );
                break;
        }
    }

    @OnClose
    public void onClose(Session session) {
        try {
            for (User user : User.getUserList()) {
                if (user.getId().equals(player.getId())) {
                    System.out.println("유저 삭제");
                    User.getUserList().remove(user);
                    break;
                }
            }
            Player.getPlayerArrayList().remove(player);

            webSocketSessionMap.remove(session.getId(), session);
            autoExitRoom();

            System.out.println("onClose()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable throwable, Session session) throws IOException {
        System.out.println("onError()");
        throwable.printStackTrace();
    }

    private void autoExitRoom() {
        System.out.println("log : player.isInRoom() : " + player.isInRoom());
        if (player.isInRoom() == true) {

            roomAction = new RoomAction(targetRoom);
            targetRoom.deletePlayer(player);

            if (targetRoom.getTotalUserNumber() == 0) {
                if (webSocketSessionMap.size() != 0) {
                    sendMessageToAllSession(RoomController.removeRoomByJSON(targetRoom));
                }
                Room.getRoomList().remove(targetRoom);
            } else {

                if (player.isMaster()) {
                    targetRoom.setRoomMaster(roomAction.getRandomExaminer());
                }

                sendMessageToRoomMembers(roomAction,
                        RoomController.getRoomInfoToJSON(targetRoom)
                );
            }
        }
    }

    private String getConnectUserListToJSON() {
        JSONObject message = new JSONObject();
        message.put("type", "USER_LIST");

        JSONObject data = new JSONObject();
        JSONArray dataArray = new JSONArray();

        for (User user : User.getUserList()) {
            JSONObject tempObject = new JSONObject();
            tempObject.put("level", user.getLevel());
            tempObject.put("name", user.getNick());
            tempObject.put("isGuest", user.isGuest());
            dataArray.put(tempObject);
        }

        data.put("userList", dataArray);
        message.put("data", data);

        return message.toString();
    }

    private void sendMessageToAllSession(String message) {
        try {
            for (String sessionId : webSocketSessionMap.keySet()) {
                webSocketSessionMap.get(sessionId).getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToRoomMembers(RoomAction roomAction, String message) {
        try {
            if (roomAction != null) {
                LinkedList<String> roomMembers;
                roomMembers = roomAction.getPlayerSessionId();

                for (String playerSessionId : roomMembers) {
                    webSocketSessionMap.get(playerSessionId).getBasicRemote().sendText(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}