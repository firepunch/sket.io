package sket.model.action;

import org.json.JSONObject;
import sket.model.data.Player;
import sket.model.data.Room;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by KwonJH on 2017-04-28.
 */
public class RoomAction {

    private Room targetRoom = null;

    public RoomAction(Room targetRoom) {
        this.targetRoom = targetRoom;
    }

    /* 방 안에 있는 player 세션을 반환한다. */
    public LinkedList<String> getPlayerSessionId() {
        LinkedList<String> linkedList = new LinkedList<>();
        for (Player player : Room.getRoomIntoPlayer(targetRoom)) {
            linkedList.add(player.getSessionID());
        }

        return linkedList;
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

    public static Room getRandomRoom(String userId) {
        double randomValue = Math.random();
        int rand = 0;
        if (Room.getRoomList().size() != 0) {
            rand = (int) (randomValue * Room.getRoomList().size()) + 1;
            for (Room targetRoom : Room.getRoomList()) {
                if (targetRoom.getRoomId() == rand) {
                    targetRoom.addPlayer(PlayerAction.getEqualPlayerId(userId));
                    return targetRoom;
                }
            }
        }

        return null;
    }

    public  Player getRandomExaminer() {
        int countUser = targetRoom.getTotalUserNumber();

        int randValue = (int) (Math.random() * countUser) + 1;
        ArrayList<Player> roomMembers = targetRoom.getPlayerList();

        Player targetPlayer = roomMembers.get(randValue - 1);
        return targetPlayer;
    }
}
