package sket.controllers;

import org.json.JSONObject;
import sket.model.data.Player;
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
public class WebSocket extends HttpServlet {

    // session 저장하는 ArrayList
    private static ArrayList<Session> sessionList = new ArrayList<>();
    private Room targetRoom;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println("onOpen()");
        sessionList.add(session);

        // session 에 룸 리스트 보냄
        session.getBasicRemote().sendText(Room.getRoomListAsJSON());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("OnMessage(" + message + ")");
        JSONObject jsonObject = new JSONObject(message);


        switch (jsonObject.getString("type")) {

                /* 방 생성 했을 때 보내는 JSON */
            case "createRoom":
                targetRoom = RoomController.createRoom(jsonObject.getString("name"), jsonObject.getBoolean("lock"),
                        jsonObject.getString("password"), session);
                session.getBasicRemote().sendText(targetRoom.getRoomInfoToJSON().put("type", "roomInfo").toString());
                break;

                /* 방 입장할 때 보내는 JSON */
            case "enterRoom":
                targetRoom = RoomController.enterRoom(jsonObject.getInt("roomId"), session);

                if (targetRoom != null) {
                    ArrayList<Session> roomMembers = targetRoom.getPlayerSession();
                    for (Session member : roomMembers) {
                        member.getBasicRemote().sendText(targetRoom.getRoomInfoToJSON().put("type", "roomInfo").toString());
                    }
                }
                break;

                /* 준비 했을 때 보내는 JSON */
            case "isReady":
                // 준비 할 때마다 방 전체 인원이 레디 했는지 검사해서 모두 준비를 한 상태면 게임 시작 JSON 을 보낸다.
                targetRoom = RoomController.findRoomById(jsonObject.getInt("roomId"));

                String readyJSON = PlayerController.gameReadyToJSON(jsonObject.getInt("roomId"), jsonObject.getBoolean("isReady"), session);
                for (Session player : targetRoom.getPlayerSession()) {
                    player.getBasicRemote().sendText(readyJSON);
                }

                String readyAllJSON = PlayerController.checkReadyAllPlayer(targetRoom);

                if (readyAllJSON != null) {
                    targetRoom.getRoomMaster().getSession().getBasicRemote().sendText(readyAllJSON);
                }
                break;

                /* 정답 맞췄을 시에 보내는 JSON */
            case "correctAnswer":
                targetRoom = RoomController.findRoomById(jsonObject.getInt("roomId"));

                if (targetRoom != null) {
                    ArrayList<Session> roomMembers = targetRoom.getPlayerSession();
                    for (Session member : roomMembers) {
                        member.getBasicRemote().sendText(QuizController.correctAnswer(jsonObject.getString("correcterId"),
                                jsonObject.getString("examinerId"), jsonObject.getInt("score")));
                    }
                }
                break;

                /* 출제자 랜덤 JSON */
            case "randomExaminer":
                targetRoom = RoomController.findRoomById(jsonObject.getInt("roomId"));

                if (targetRoom != null) {
                    ArrayList<Session> roomMembers = targetRoom.getPlayerSession();
                    for (Session member : roomMembers) {
                        member.getBasicRemote().sendText(GameController.randomExaminerToJSON(jsonObject.getString("id"), targetRoom.getRoomId()));
                    }
                }
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
