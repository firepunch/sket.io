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
    private ArrayList<Player> playerList = new ArrayList<>();
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
            this.roomPwd = "null";
        }

        this.countRoomId += 1;

        System.out.println("log : " + "방 생성 성공");

        Room.roomList.add(this);
        totalUserNumber += 1;

        playerList.add(roomMaster);
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

    public static ArrayList<Player> getRoomIntoPlayer(Room targetRoom) {
        return targetRoom.playerList;
    }

    public static int getCountRoomId() {
        return countRoomId;
    }

    public void addPlayer(Player player) {
        playerList.add(player);
        totalUserNumber += 1;
    }

    public void deletePlayer(Player player) {
        playerList.remove(player);
        totalUserNumber -= 1;
    }

    public void deletePlayer(String id) {
        for (Player player : playerList) {
            if (player.getId().equals(id)) {
                playerList.remove(player);
                totalUserNumber -= 1;
            }
        }
    }

}
