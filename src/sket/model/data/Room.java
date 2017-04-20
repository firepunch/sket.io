package sket.model.data;

import org.json.JSONArray;
import org.json.JSONObject;
import sun.rmi.runtime.Log;

import javax.websocket.Session;
import java.util.ArrayList;

/**
 * Created by hojak on 2017-04-07.
 * 방에 대한 정보를 나타내는 클래스
 */
public class Room {

    // Room 객체를 저장하는 ArrayList
    private static ArrayList<Room> roomList = new ArrayList<>();

    // Room 에 존재하는 User 를 ArrayList 에 저장
    private ArrayList<Player> playeList;
    private final int MAX_USER = 4;
    private static int countRoomId = 0;

    private boolean isLock = false;
    private int totalUserNumber = 0;
    private int roomId;
    private String roomName;
    private String roomPwd;
    private Player roomMaster;


    public Room(String name, Player roomMaster, int roomId, boolean isLock, String pwd) {
        this.roomName = name;
        this.roomMaster = roomMaster;
        this.roomId = roomId;
        this.isLock = isLock;

        if (isLock == true) {
            if (pwd != null) {
                this.roomPwd = pwd;
            }
        } else {
            this.roomPwd = null;
        }

        this.countRoomId += 1;
        System.out.println("방 생성 성공");
        Room.roomList.add(this);
    }


    /* roomList 에서 인자로 들어온 room 객체 찾아서 반환하는 메소드 */
    public Room getEqualRoom(Room room) {

        Room tempRoom = null;

        for (Room tmp : Room.roomList) {
            if (tmp.getRoomId() == room.getRoomId()) {
                tempRoom = tmp;
            }
        }

        if (tempRoom == null) {
            System.out.println("찾는 Room 없음");
            return null;
        }

        return tempRoom;
    }

    public static String getRoomListAsJSON() {
        JSONObject message = new JSONObject();
        message.put("type", "roomList");
        JSONArray jsonArray = new JSONArray();

        for (Room room : roomList) {
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

    public JSONObject getRoomInfoToJSON() {
        JSONObject object = new JSONObject();
        object.put("roomId", roomId);
        object.put("name", roomName);
        object.put("lock", isLock);
        object.put("password", roomPwd);
        object.put("playerNumber", totalUserNumber);
        object.put("roomMaster", roomMaster.getId());
        JSONArray jsonArray = new JSONArray();

        for (Player player : playeList) {
            JSONObject temp = new JSONObject();
            temp.put("id", player.getId());
            jsonArray.put(temp);
        }
        object.put("playerList", jsonArray);
        return object;
    }

    public static ArrayList<Room> getRoomList() {
        return Room.roomList;
    }

    public boolean isLocked() {
        return this.isLock;
    }

    public int getTotalUserNumber() {
        return this.totalUserNumber;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public String getRoomPwd() {
        return this.roomPwd;
    }

    public Player getRoomMaster() {
        return this.roomMaster;
    }

    public ArrayList<Player> getRoomIntoPlayer() {
        return this.playeList;
    }

    public static int getCountRoomId() {
        return countRoomId;
    }

    public void addPlayer(Player player) {
        playeList.add(player);
    }

    public ArrayList<Session> getPlayerSession() {
        ArrayList<Session> sessionArrayList = new ArrayList<>();
        for (Player player : getRoomIntoPlayer()) {
            sessionArrayList.add(player.getSession());
        }
        return sessionArrayList;
    }

}
