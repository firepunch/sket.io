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
    // 게임 플레이 할 때 사용됨
    // session 저장하는 ArrayList
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
        sessionList.add(session);
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        System.out.println("log : " + "getUserIdEqualSession() : " + SessionManager.getUserIdEqualSession(httpSession));
        if (((User) httpSession.getAttribute("user")).isGuest()) {
            player = new Player(false, session, true);
        } else {
            player = new Player(SessionManager.getUserIdEqualSession(httpSession), false, session, false);
        }
*/

/*
        String id = ((User) session.getAttribute("user")).getId();
        // DB에 유저의 정보가 저장되었는지 확인하여 게스트 구분
        if (db.isGuest(id)) {
            player = new Player(session.getId(), true);
        } else {
            player = new Player(id, session.getId(), false);
        }
*/

//         session 에 룸 리스트 보냄
        rcvsession.getBasicRemote().sendText(RoomController.getRoomListAsJSON());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("OnMessage( " + message + " )");
        JSONObject jsonObject = new JSONObject(message);
        switch (jsonObject.getString("type")) {

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