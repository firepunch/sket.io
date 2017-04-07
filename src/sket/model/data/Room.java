package sket.model.data;

import java.util.ArrayList;

/**
 * Created by hojak on 2017-04-07.
 * 방에 대한 정보를 나타내는 클래스
 */
public class Room {
    private static ArrayList<Room> roomList = new ArrayList<>();
    private ArrayList<User> userList;
    private final int MAX_USER = 4;
    private boolean isLock = false;

    private int totalUserNumber;
    private int roomNumber;
    private String roomName;
    private String roomPwd;
    private User roomMaster;

    public Room(String name, String pwd, User roomMaster, boolean isLock, int roomNum){
        this.roomName = name;
    }

}
