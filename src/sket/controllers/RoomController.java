package sket.controllers;

import sket.model.data.Player;
import sket.model.data.Room;

import javax.websocket.Session;

public class RoomController {

    public RoomController() {
        super();
    }

    /* 방 생성하는 메소드 */
    public static Room createRoom(String name, boolean islock, String pwd, Session session) {

        // 방 생성 코드. Room 생성자 안에 roomList 에 방 추가하는 코드 작성되있음.
        Room room = new Room(name, Player.getPlayerEqualSession(session), Room.getCountRoomId(), islock, pwd);
        return room;
    }

    /* 유저가 방 입장하는 것을 처리하는 메소드 */
    public static Room enterRoom(int roomId, Session session) {
        Room enter = RoomController.findRoomById(roomId);

        if (enter != null) {
            enter.addPlayer(Player.getPlayerEqualSession(session));
            return enter;
        }

        return null;
    }

    /* 인자로 들어온 roomId 를 이용해 Room 객체를 찾아 반환해주는 메소드 */
    public static Room findRoomById(int roomId) {
        for (Room room : Room.getRoomList()) {
            if (room.getRoomId() == roomId) {
                return room;
            }
        }
        return null;
    }
}