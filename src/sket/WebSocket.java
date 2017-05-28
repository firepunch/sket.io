package sket;

import org.json.JSONArray;
import org.json.JSONObject;
import sket.controllers.GameController;
import sket.controllers.PlayerController;
import sket.controllers.QuizController;
import sket.controllers.RoomController;
import sket.db.DBConnection;
import sket.model.action.PlayerAction;
import sket.model.action.QuizAction;
import sket.model.action.RoomAction;
import sket.model.action.SessionManager;
import sket.model.data.Player;
import sket.model.data.Room;
import sket.model.data.User;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 게임 메인화면에 연결되는 웹 소켓
 * Created by hojak on 2017-04-06.
 */

@ServerEndpoint(value = "/websocket", configurator = GetHttpSessionConfigurator.class)
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
    public void onOpen(Session rcvSession, EndpointConfig config) throws IOException, SQLException {
        System.out.println("HttpSessionList Size : " + SessionManager.getSessionList().size());

        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        webSocketSessionMap.put(rcvSession.getId(), rcvSession);

        System.out.println("log : HttpSession : " + httpSession + "\n" +
                "ID : " + SessionManager.getUserIdEqualSession(httpSession));

//        if (((User) httpSession.getAttribute("user")).isGuest()) {
//            player = new Player(((User) httpSession.getAttribute("user")).getId(), httpSession.getId(), true);
//        } else {
//            player = new Player(((User) httpSession.getAttribute("user")).getId(), httpSession.getId(), false);
//        }

        // session 에 룸 리스트 보냄
        rcvSession.getBasicRemote().sendText(RoomController.getRoomListAsJSON());
        rcvSession.getBasicRemote().sendText(getConnectUserListToJSON());

    }

    @OnMessage
    public void onMessage(String message, Session rcvSession) throws IOException, SQLException {
        // 세션리스트에 접속한 세션 추가, Player객체 생성
        // 접속 유저, 생성된 방의 정보 전송

        System.out.println("OnMessage( " + message + " )");
        JSONObject jsonObject = new JSONObject(message);

        switch (jsonObject.getString("type")) {

            // 방 생성 했을 때 보내는 JSON
            case "CREATE_ROOM":
                targetRoom = RoomController.createRoom(
                        jsonObject.getString("roomName"),
                        jsonObject.getBoolean("lock"),
                        jsonObject.getString("password"),
                        jsonObject.getString("master")
                );

                rcvSession.getBasicRemote().sendText(
                        RoomController.getRoomInfoToJSON(targetRoom).put("type", "ROOM_INFO").toString()
                );
                break;

            // 방 리스트 보내는 JSON
            case "ROOM_LIST":
                rcvSession.getBasicRemote().sendText(RoomController.getRoomListAsJSON());
                break;

            // 방 들어갔을 때 보내는 JSON
            case "ENTER_ROOM":
                targetRoom = RoomAction.enterRoom(
                        jsonObject.getInt("roomId"),
                        jsonObject.getString("userId")
                );

                roomAction = new RoomAction(targetRoom);

                if (targetRoom != null && roomAction != null) {
                    roomMembers = roomAction.getPlayerSessionId();

                    for (String playerSessionId : roomMembers) {
                        playerSession = webSocketSessionMap.get(playerSessionId);

                        playerSession.getBasicRemote().sendText(
                                RoomController.getRoomInfoToJSON(targetRoom).put("type", "ROOM_INFO").toString()
                        );
                    }
                }
                break;

            // 플레이어가 방에서 준비했을 때 보내는 JSON. 만약 방장 제외 모두 준비했을 시 방장에거 모두 준비했다고 알림!
            case "GET_READY":
                targetRoom = RoomAction.findRoomById(jsonObject.getInt("roomId"));
                roomAction = new RoomAction(targetRoom);


                String readyJSON = PlayerController.gameReadyToJSON(
                        jsonObject.getInt("roomId"),
                        jsonObject.getBoolean("isReady"),
                        rcvSession.getId()
                );

                for (String playerSessionId : roomAction.getPlayerSessionId()) {
                    playerSession = webSocketSessionMap.get(playerSessionId);
                    playerSession.getBasicRemote().sendText(readyJSON);
                }

                String readyAllPlayerJSON = PlayerController.checkReadyAllPlayer(targetRoom);

                if (readyAllPlayerJSON != null) {
                    Session tempSession = webSocketSessionMap.get(targetRoom.getRoomMaster().getSessionID());
                    System.out.println("log : HashMap.get() : " + tempSession);

                    tempSession.getBasicRemote().sendText(readyAllPlayerJSON);
                }
                break;

            // 플레이어가 문제 맞혔을 때 보내는 JSON
            case "CORRECT_ANSWER":
                targetRoom = RoomAction.findRoomById(jsonObject.getInt("roomId"));
                roomAction = new RoomAction(targetRoom);

                if (targetRoom != null) {
                    roomMembers = roomAction.getPlayerSessionId();
                    for (String playerSessionId : roomMembers) {
                        playerSession = webSocketSessionMap.get(playerSessionId);
                        playerSession.getBasicRemote().sendText(
                                QuizController.correctAnswer(
                                        jsonObject.getString("correcterId"),
                                        jsonObject.getString("examinerId"),
                                        jsonObject.getInt("score"))
                        );
                    }
                }
                break;

            // 랜덤 출제자 선택해서 JSON 보냄
            case "RANDOM_EXAMINER":
                targetRoom = RoomAction.findRoomById(jsonObject.getInt("roomId"));
                roomAction = new RoomAction(targetRoom);

                if (targetRoom != null) {
                    roomMembers = roomAction.getPlayerSessionId();
                    for (String playerSessionId : roomMembers) {
                        playerSession = webSocketSessionMap.get(playerSessionId);
                        playerSession.getBasicRemote().sendText(GameController.randomExaminerToJSON(
                                jsonObject.getString("id"),
                                targetRoom.getRoomId()
                        ));
                    }
                }
                break;

            // 랜덤 문제 JSON 보냄
            case "RANDOM_QUIZ":
                targetRoom = RoomAction.findRoomById(jsonObject.getInt("roomId"));
                roomAction = new RoomAction(targetRoom);

                if (targetRoom != null) {
                    Player targetPlayer = PlayerAction.getEqualPlayerId(jsonObject.getString("id"));
                    playerSession = webSocketSessionMap.get(targetPlayer.getSessionID());
                    playerSession.getBasicRemote().sendText(QuizController.sendQuizByJSON());
                }
                break;

            // 캔바스 데이터 JSON 보냄
            case "CANVAS_DATA":
                roomMembers = QuizAction.excludeExaminerSession(jsonObject.getString("id"));

                if (roomMembers != null) {
                    for (String playerSessionId : roomMembers) {
                        playerSession = webSocketSessionMap.get(playerSessionId);
                        playerSession.getBasicRemote().sendText(QuizController.sendCanvasData());
                    }
                }
                break;

            // 채팅 JSON
            case "CHAT_START":
                targetRoom = RoomAction.findRoomById(jsonObject.getInt("roomId"));
                roomAction = new RoomAction(targetRoom);

                roomMembers = roomAction.getPlayerSessionId();
                for (String playerSessionId : roomMembers) {
                    playerSession = webSocketSessionMap.get(playerSessionId);
                    playerSession.getBasicRemote().sendText(jsonObject.getString("msg"));
                }

                break;

            // 랭킹 JSON
            case "SHOW_RANK":
                JSONObject rankInfo = new JSONObject();
                DBConnection db = new DBConnection();
                rankInfo = db.showRank(jsonObject.getString("userId"));
                System.out.println(rankInfo);
                rcvSession.getBasicRemote().sendText(String.valueOf(rankInfo));
        }
    }

    @OnClose
    public void onClose(Session session) {
        webSocketSessionMap.remove(session.getId(), session);
        System.out.println("onClose()");
    }

    @OnError
    public void onError(Throwable throwable, Session session) {
        webSocketSessionMap.remove(session.getId(), session);

        System.out.println("onError()");
        throwable.printStackTrace();
    }


    private String getConnectUserListToJSON() {
        JSONObject message = new JSONObject();
        JSONArray dataArray = new JSONArray();

        message.put("type", "USER_LIST");


        for (HttpSession playerHttpSession : SessionManager.getSessionList()) {
            int level = ((User) playerHttpSession.getAttribute("user")).getLevel();
            String playerId = ((User) playerHttpSession.getAttribute("user")).getNick();

            JSONObject tempObject = new JSONObject();
            tempObject.put("level", level);
            tempObject.put("playerId", playerId);

            dataArray.put(tempObject);
        }

        message.put("data", dataArray);

        return message.toString();
    }

}