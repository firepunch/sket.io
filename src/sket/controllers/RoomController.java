package sket.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import sket.model.action.PlayerAction;
import sket.model.data.Player;
import sket.model.data.Room;

public class RoomController {

    public RoomController() {
        super();
    }

    public static String noEnterRoom(String id) {
        JSONObject message = new JSONObject();
        message.put("type", "NO_ENTER_ROOM");

        JSONObject data = new JSONObject();
        data.put("id", id);
        message.put("data", data);

        return message.toString();
    }

    /* 방 인원 0명일 시 방 삭제 */
    public static String removeRoomByJSON(Room targetRoom) {
        JSONObject message = new JSONObject();
        message.put("type", "REMOVE_ROOM");

        JSONObject data = new JSONObject();
        data.put("roomId", targetRoom.getRoomId());
        message.put("data", data);

        return message.toString();
    }


    /* 방 생성하는 메소드 */
    public static Room createRoom(String name, boolean isLock, String pwd, String masterId, int userMax, int timeLimit, int roundLimit) {
        // 방 생성 코드. Room 생성자 안에 roomList 에 방 추가하는 코드 작성되있음.
        Room room = new Room(
                name, PlayerAction.getEqualPlayerId(masterId),
                Room.getCountRoomId(),
                isLock, pwd, userMax, timeLimit, roundLimit);
        Player player = PlayerAction.getEqualPlayerId(masterId);
        player.setMaster(true);

        return room;
    }

    /* 방 정보 json 으로 반환하는 메소드 */
    public static String getRoomInfoToJSON(Room targetRoom) {
        JSONObject message = new JSONObject();
        message.put("type", "ROOM_INFO");

        JSONObject data = new JSONObject();
        data.put("roomId", targetRoom.getRoomId());
        data.put("roomName", targetRoom.getRoomName());
        data.put("numRound", targetRoom.getRoundLimit());
        data.put("timeLimit", targetRoom.getTimeLimit());
        data.put("userNumLimit", targetRoom.getUserMax());
        data.put("roomMaster", targetRoom.getRoomMaster().getId());
        data.put("userNum", targetRoom.getTotalUserNumber());
        JSONArray jsonArray = new JSONArray();

        for (Player player : Room.getRoomIntoPlayer(targetRoom)) {
            JSONObject temp = new JSONObject();
            temp.put("nick", player.getNickname());
            temp.put("picture", player.getPicture());
            temp.put("level", player.getPlayerLevel());
            temp.put("isReady", player.isReady());
            temp.put("id", player.getId());
            temp.put("master", player.isMaster());
            temp.put("score", player.getScore());
            jsonArray.put(temp);
        }

        data.put("playerList", jsonArray);
        message.put("data", data);
        return message.toString();
    }

    /* 방 목록을 json 으로 보내는 메소드 */
    public static String getRoomListAsJSON() {
        System.out.println("log : 방 목록 리스트 사이즈 : " + Room.getRoomList().size());

        JSONObject message = new JSONObject();
        message.put("type", "ROOM_LIST");

        JSONObject data = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        if (Room.getRoomList().size() == 0) {
            data.put("size", 0);
            message.put("data", data);
            return message.toString();
        }

        for (Room room : Room.getRoomList()) {
            if (room.isPlayingGame() == false) {
                JSONObject object = new JSONObject();
                object.put("roomId", room.getRoomId());
                object.put("roomName", room.getRoomName());
                object.put("round", room.getCurRound());
                object.put("roundLimit", room.getRoundLimit());
                object.put("timeLimit", room.getTimeLimit());
                object.put("userNumLimit", room.getUserMax());
                object.put("userNum", room.getTotalUserNumber());
                object.put("isLocked", room.isLocked());
                object.put("password", room.getRoomPwd());

                jsonArray.put(object);
            }
        }

        data.put("roomList", jsonArray);
        message.put("data", data);

        System.out.println(message.toString());
        return message.toString();
    }

    public static String getRoomStartQuizToJSON(int round, boolean isGameEnd) {
        JSONObject message = new JSONObject();
        message.put("type", "START_QUIZ");

        JSONObject data = new JSONObject();
        data.put("round", round);
        data.put("gameEnd", isGameEnd);

        message.put("data", data);
        return message.toString();
    }
}