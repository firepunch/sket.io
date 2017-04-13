package sket.controllers;

import sket.model.data.Player;
import sket.model.data.Room;
import sket.model.data.User;

import javax.websocket.Session;

public class RoomController {

    public RoomController() {
        super();
    }

    public static Room createRoom(String name, boolean islock, String pwd, Session session) {

        // 방 생성 코드. Room 생성자 안에 roomList 에 방 추가하는 코드 작성되있음.
        Room room = new Room(name, Player.getPlayerEqualSession(session), Room.getCountRoomId(), islock, pwd);
        return room;
    }

}
