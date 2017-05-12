package sket.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import sket.model.action.PlayerAction;
import sket.model.data.Player;
import sket.model.data.Room;

import javax.websocket.Session;

public class RoomController {

    public RoomController() {
        super();
    }

    /* 방 생성하는 메소드 */
    public static Room createRoom(String name, boolean isLock, String pwd, String masterId, Session session) {
        // 방 생성 코드. Room 생성자 안에 roomList 에 방 추가하는 코드 작성되있음.
        Player player = new Player(masterId, true, session, false);
        Room room = new Room(name, PlayerAction.getPlayerEqualSession(session), Room.getCountRoomId(), isLock, pwd);
        return room;
    }

    /* 방 정보 json 으로 반환하는 메소드 */
    public static JSONObject getRoomInfoToJSON(Room targetRoom) {
        JSONObject object = new JSONObject();
        object.put("roomId", targetRoom.getRoomId());
        object.put("name", targetRoom.getRoomName());
        object.put("lock", targetRoom.isLocked());
        object.put("password", targetRoom.getRoomPwd());
        object.put("playerNumber", targetRoom.getTotalUserNumber());
        object.put("roomMaster", targetRoom.getRoomId());
        JSONArray jsonArray = new JSONArray();

        for (Player player : Room.getRoomIntoPlayer(targetRoom)) {
            JSONObject temp = new JSONObject();
            temp.put("id", player.getId());
            jsonArray.put(temp);
        }

        object.put("playerList", jsonArray);
        return object;
    }

    /* 방 목록을 json 으로 보내는 메소드 */
    public static String getRoomListAsJSON() {
        JSONObject message = new JSONObject();
        message.put("type", "roomList");
        JSONArray jsonArray = new JSONArray();

        for (Room room : Room.getRoomList()) {
            JSONObject object = new JSONObject();
            object.put("roomId", room.getRoomId());
            object.put("name", room.getRoomName());
            object.put("lock", room.isLocked());
            object.put("password", room.getRoomPwd());
            object.put("playerNumber", room.getTotalUserNumber());

            jsonArray.put(object);
        }
        message.put("roomList", jsonArray);
        return message.toString();
    }
}