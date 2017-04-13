package sket.controllers;

import org.json.JSONObject;
import sket.model.data.Room;

import javax.servlet.http.HttpServlet;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hojak on 2017-04-06.
 */

@ServerEndpoint("/test")
public class WebSocket extends HttpServlet{

    // session 저장하는 ArrayList
    private static ArrayList<Session> sessionList = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println("onOpen()");
        sessionList.add(session);

        // session 에 룸 리스트 보냄
        session.getBasicRemote().sendText(Room.getRoomListAsJSON());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("OnMessage("+message+")");
        JSONObject jsonObject = new JSONObject(message);

        switch (jsonObject.getString("type")){
            case "createRoom" : {
                Room createRoom = RoomController.createRoom(jsonObject.getString("name"), jsonObject.getBoolean("lock"),
                        jsonObject.getString("password"), session);
                //session.getBasicRemote().sendText(createRoom.getRoomListAsJSON().put("type",""))
            }
            break;

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
