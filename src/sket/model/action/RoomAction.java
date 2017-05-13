package sket.model.action;

import sket.controllers.RoomController;
import sket.model.data.Player;
import sket.model.data.Room;

import javax.websocket.Session;
import java.util.ArrayList;

/**
 * Created by KwonJH on 2017-04-28.
 */
public class RoomAction {

    private Room targetRoom = null;

    public RoomAction(Room targetRoom) {
        this.targetRoom = targetRoom;
    }

    /* 방 안에 있는 player 세션을 반환한다. */
    public ArrayList<Session> getPlayerSession() {
        ArrayList<Session> sessionArrayList = new ArrayList<>();
        for (Player player : Room.getRoomIntoPlayer(targetRoom)) {
            sessionArrayList.add(player.getSession());
        }
        return sessionArrayList;
    }

    /* 방 리스트 중에 인자로 들어온 방을 리턴해준다. 만약 없으면 null 반환 */
    public static Room getEqualRoom(Room targetRoom) {

        Room tempRoom = null;

        for (Room tmp : Room.getRoomList()) {
            if (tmp.getRoomId() == targetRoom.getRoomId()) {
                tempRoom = tmp;
            }
        }

        if (tempRoom == null) {
            System.out.println("찾는 Room 없음");
            return null;
        }

        return tempRoom;
    }

    /* 유저가 방 입장하는 것을 처리하는 메소드 */
    public static Room enterRoom(int roomId, String userId) {
        Room enter = RoomAction.findRoomById(roomId);

        if (enter != null) {
            enter.addPlayer(PlayerAction.getEqualPlayerId(userId));
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
