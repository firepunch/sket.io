package sket;

import org.json.JSONObject;
import sket.controllers.PlayerController;
import sket.controllers.RoomController;
import sket.db.DBConnection;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hojak on 2017-04-06.
 */


/*
 * 게임 메인화면에 연결되는 웹 소켓이다.
 * */
@ServerEndpoint(value = "/websocket", configurator = GetHttpSessionConfigurator.class)
public class WebSocket {

    // 현재 웹소켓에 연결되어 있는 session 을 저장하는 ArrayList (HttpSession 과 다름)
    private static Map<String, Session> webSocketSessionMap = new HashMap<>();
    private ArrayList<Session> roomMembers;

    private Room targetRoom = null;
    private RoomAction roomAction = null;
    private Player player;

    // 세션리스트에 접속한 세션 추가, Player 객체 생성, 생성된 룸 정보 보냄
    @OnOpen
    public void onOpen(Session rcvSession, EndpointConfig config) throws IOException, SQLException {

        // DBConnection db = new DBConnection();

        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        webSocketSessionMap.put(rcvSession.getId(), rcvSession);

        System.out.println("log : HttpSession : " + httpSession + "\n" +
                "ID : " + SessionManager.getUserIdEqualSession(httpSession));

        String id = ((User) httpSession.getAttribute("user")).getId();

        if (((User) httpSession.getAttribute("user")).isGuest()) {
            player = new Player(((User) httpSession.getAttribute("user")).getId(), httpSession.getId(), true);
        } else {
            player = new Player(((User) httpSession.getAttribute("user")).getId(), httpSession.getId(), false);
        }
/*
        // DB에 유저의 정보가 저장되었는지 확인하여 게스트 구분
        if (db.isGuest(id)) {
            player = new Player(id, httpSession.getId(), true);
        } else {
            player = new Player(id, rcvSession.getId(), false);
        }
*/
        // session 에 룸 리스트 보냄
        rcvSession.getBasicRemote().sendText(RoomController.getRoomListAsJSON());
    }

    @OnMessage
    public void onMessage(String message, Session rcvSession) throws IOException {
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

            case "ENTER_ROOM":
                targetRoom = RoomAction.enterRoom(
                        jsonObject.getInt("roomId"),
                        jsonObject.getString("userId")
                );

                roomAction = new RoomAction(targetRoom);

                if (targetRoom != null && roomAction != null) {
                    roomMembers = roomAction.getPlayerSession();
                    for (Session member : roomMembers) {
                        member.getBasicRemote().sendText(
                                RoomController.getRoomInfoToJSON(targetRoom).put("type", "ROOM_INFO").toString()
                        );
                    }
                }
                break;

            case "GET_READY":
                targetRoom = RoomAction.findRoomById(jsonObject.getInt("roomId"));
                roomAction = new RoomAction(targetRoom);

                String readyJSON = PlayerController.gameReadyToJSON(
                        jsonObject.getInt("roomId"),
                        jsonObject.getBoolean("isReady"),
                        rcvSession.getId()
                );

                for (Session player : roomAction.getPlayerSession()) {
                    player.getBasicRemote().sendText(readyJSON);
                }

                String readyAllPlayerJSON = PlayerController.checkReadyAllPlayer(targetRoom);

                if (readyAllPlayerJSON != null) {
                    Session tempSession = (Session) webSocketSessionMap.get(targetRoom.getRoomMaster().getSessionID());
                    System.out.println("log : HashMap.get() : " + tempSession);

                    tempSession.getBasicRemote().sendText(readyAllPlayerJSON);
                }
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


}