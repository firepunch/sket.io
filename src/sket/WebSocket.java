package sket;

import org.json.JSONObject;
import sket.controllers.RoomController;
import sket.db.DBConnection;
import sket.model.action.RoomAction;
import sket.model.action.SessionManager;
import sket.model.data.Guest;
import sket.model.data.Player;
import sket.model.data.Room;
import sket.model.data.User;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by hojak on 2017-04-06.
 */


@ServerEndpoint(value = "/websocket", configurator = GetHttpSessionConfigurator.class)
public class WebSocket {
    //게임 플레이 할 때 사용됨
    //session 저장하는 ArrayList
    private static ArrayList<Session> sessionList = new ArrayList<>();
    private Room targetRoom = null;
    private RoomAction roomAction = null;
    private Player player;
    private Guest guest;
    ArrayList<Session> roomMembers;

    //  세션리스트에 접속한 세션 추가, Player객체 생성, 생성된 룸 정보 보냄
    @OnOpen
    public void onOpen(Session rcvsession, EndpointConfig config) throws IOException, SQLException {
        DBConnection db = new DBConnection();
        sessionList.add(rcvsession);
        System.out.println("log : onOpen()");
        System.out.println("session list in websocket\n   " + sessionList);
//        System.out.println("get sessino name \n   " + HttpSession.class.getName());
//        System.out.println("config.getUserProperties() \n   " + config.getUserProperties());

        HttpSession session = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        System.out.println("session in websocket onopen\n     " + session);
        System.out.println("log : " + "getUserIdEqualSession() : " + SessionManager.getUserIdEqualSession(session));

/*
        TODO: ㅇㅁㄹ
        if (((User) httpSession.getAttribute("user")).isGuest()) {
            player = new Player(false, session, true);
        } else {
            player = new Player(SessionManager.getUserIdEqualSession(httpSession), false, session, false);
        }
*/
        String id = ((User) session.getAttribute("user")).getId();
        // DB에 유저의 정보가 저장되었는지 확인하여 게스트 구분
        if (db.isGuest(id)) {
            player = new Player(session.getId(), true);
        } else {
            player = new Player(id, session.getId(), false);
        }

//         session 에 룸 리스트 보냄
        rcvsession.getBasicRemote().sendText(RoomController.getRoomListAsJSON());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("OnMessage( " + message + " )");
        JSONObject jsonObject = new JSONObject(message);

        switch (jsonObject.getString("type")) {

            /* oauth 로그인 */
            case "oauthLogin":
                System.out.println("log : message : testttT");
                session.getBasicRemote().sendText("FROM SERVER: test");

            /* 방 생성 했을 때 보내는 JSON */
            case "createRoom":
                targetRoom = RoomController.createRoom(jsonObject.getString("roomName"), jsonObject.getBoolean("lock"),
                        jsonObject.getString("password"), jsonObject.getString("master"));

                session.getBasicRemote().sendText(RoomController.getRoomInfoToJSON(targetRoom).put("type", "roomInfo").toString());
                break;

            /* 방 리스트 보내는 JSON... 우선 테스트 코드 */
            case "roomList":
                session.getBasicRemote().sendText(RoomController.getRoomListAsJSON());
                break;

            /* Guest 만드는 JSON... 우선 테스트 코드임 */
//           TODO: ㅇㅁㄹ: 게스트 안 만들어도 되지않아? 일단 오류나서 주석처리함
//            case "guestTest":
//                Guest guest = new Guest(false, session);
//                Player guestPlayer = PlayerAction.getEqualPlayerId(guest.getId());
//
//                System.out.println("log : 게스트 아이디 : " + guest.getId());
//                System.out.println("log : 게스트&플레이어 아이디 : " + guestPlayer.getId());
//                System.out.println("log : 게스트 세션ID : " + guest.getSession());
//                System.out.println("log : 게스트&플레이어 세션 : " + guestPlayer.getSession());
//
//                break;

            /* 방 입장할 때 보내는 JSON */
//            case "enterRoom":
//                targetRoom = RoomAction.enterRoom(jsonObject.getInt("roomId"), jsonObject.getString("userId"));
//
//                roomAction = new RoomAction(targetRoom);
//
//                if (targetRoom != null && roomAction != null) {
//                    roomMembers = roomAction.getPlayerSession();
//                    for (Session member : roomMembers) {
//                        member.getBasicRemote().sendText(RoomController.getRoomInfoToJSON(targetRoom).put("type", "roomInfo").toString());
//                    }
//                }
//                break;

            /* 준비 했을 때 보내는 JSON */
//            case "isReady":
//                // 준비 할 때마다 방 전체 인원이 레디 했는지 검사해서 모두 준비를 한 상태면 게임 시작 JSON 을 보낸다.
//                targetRoom = RoomAction.findRoomById(jsonObject.getInt("roomId"));
//                roomAction = new RoomAction(targetRoom);
//
//                String readyJSON = PlayerController.gameReadyToJSON(jsonObject.getInt("roomId"), jsonObject.getBoolean("isReady"), session);
//                for (Session player : roomAction.getPlayerSession()) {
//                    player.getBasicRemote().sendText(readyJSON);
//                }
//
//                String readyAllJSON = PlayerController.checkReadyAllPlayer(targetRoom);
//
//                if (readyAllJSON != null) {
//                    targetRoom.getRoomMaster().getSession().getBasicRemote().sendText(readyAllJSON);
//                }
//                break;

            /* 정답 맞췄을 시에 보내는 JSON */
//            case "correctAnswer":
//                targetRoom = RoomAction.findRoomById(jsonObject.getInt("roomId"));
//                roomAction = new RoomAction(targetRoom);
//
//                if (targetRoom != null) {
//                    roomMembers = roomAction.getPlayerSession();
//                    for (Session member : roomMembers) {
//                        member.getBasicRemote().sendText(QuizController.correctAnswer(jsonObject.getString("correcterId"),
//                                jsonObject.getString("examinerId"), jsonObject.getInt("score")));
//                    }
//                }
//                break;

            /* 출제자 랜덤 JSON */
//            case "randomExaminer":
//                targetRoom = RoomAction.findRoomById(jsonObject.getInt("roomId"));
//                roomAction = new RoomAction(targetRoom);
//
//                if (targetRoom != null) {
//                    roomMembers = roomAction.getPlayerSession();
//                    for (Session member : roomMembers) {
//                        member.getBasicRemote().sendText(GameController.randomExaminerToJSON(jsonObject.getString("id"), targetRoom.getRoomId()));
//                    }
//                }
//                break;

            /* 출제자에게 퀴즈 보내기 */
//            case "randomQuiz":
//                targetRoom = RoomAction.findRoomById(jsonObject.getInt("roomId"));
//                roomAction = new RoomAction(targetRoom);
//
//                if (targetRoom != null) {
//                    Player targetPlayer = PlayerAction.getEqualPlayerId(jsonObject.getString("id"));
//                    targetPlayer.getSession().getBasicRemote().sendText(QuizController.sendQuizByJSON());
//                }
//                break;

            /* 방에서 출제자를 제외한 플레이어에게 캔버스 데이터 보냄 */
            case "canvasData":
//                roomMembers = QuizAction.excludeExaminerSession(jsonObject.getString("id"));
//
//                if (roomMembers != null) {
//                    for (Session member : roomMembers) {
//                        member.getBasicRemote().sendText(QuizController.sendCanvasData());
//                    }
//                }
//                break;

            case "chatStart":
//                targetRoom = RoomAction.findRoomById(jsonObject.getInt("roomId"));
//                roomAction = new RoomAction(targetRoom);
//
//                ArrayList<Session> roomMember = roomAction.getPlayerSession();
//                for (Session session1 : roomMember) {
//                    session1.getBasicRemote().sendText(jsonObject.getString("msg"));
//                }
//                break;
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose()");
    }

    @OnError
    public void onError(Throwable throwable, Session session) {
        System.out.println("onError()");
        throwable.printStackTrace();
    }
}