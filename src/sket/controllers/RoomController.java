package sket.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import sket.model.action.PlayerAction;
import sket.model.action.SessionManager;
import sket.model.data.Player;
import sket.model.data.Room;

import javax.websocket.Session;

public class RoomController {

    public RoomController() {
        super();
    }

    /* 방 생성하는 메소드 */
    public static Room createRoom(String name, boolean isLock, String pwd, String masterId) {
        // 방 생성 코드. Room 생성자 안에 roomList 에 방 추가하는 코드 작성되있음.

        Room room = new Room(name, PlayerAction.getEqualPlayerId(masterId), Room.getCountRoomId(), isLock, pwd);
        Player player = PlayerAction.getEqualPlayerId(masterId);
        player.setRoomMaster(true);

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

        System.out.println("log : 방 목록 리스트 사이즈 : " + Room.getRoomList().size());

        /* 방생성 테스트 코드 */
        // RoomController.createRoom("방제목", false, null, "아디");
        // RoomController.createRoom("방제목1", true, "123123", "아디");

        if (Room.getRoomList().size() == 0) {
            message.put("size", 0);
            return message.toString();
        }

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

        /*
            다음과 같이 json 보낸다.

            {
	        "type": "roomList",
	        "roomList": [{
		        "playerNumber": 0,
		        "password": "null",
		        "name": "방제목",
		        "lock": false,
		        "roomId": 0
	        }, {
		        "playerNumber": 0,
		        "password": "123123",
		        "name": "방제목1",
		        "lock": true,
		        "roomId": 1
	        }]
        }

        */
    }
}