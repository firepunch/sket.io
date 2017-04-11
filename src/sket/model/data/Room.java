package sket.model.data;

import sun.rmi.runtime.Log;

import java.util.ArrayList;

/**
 * Created by hojak on 2017-04-07.
 * 방에 대한 정보를 나타내는 클래스
 */
public class Room {

    // Room 객체를 저장하는 ArrayList
    private static ArrayList<Room> roomList = new ArrayList<>();

    // Room 에 존재하는 User 를 ArrayList 에 저장
    private ArrayList<User> userList;
    private final int MAX_USER = 4;

    private boolean isLock = false;
    private int totalUserNumber = 0;
    private int roomNumber;
    private String roomName;
    private String roomPwd;
    private User roomMaster;

    public Room(String name, User roomMaster, int roomNum, boolean isLock, String pwd){
        this.roomName = name;
        this.roomMaster = roomMaster;
        this.roomNumber = roomNum;
        this.isLock = isLock;

        if(isLock == true) {
            if(pwd != null){
                this.roomPwd = pwd;
            }
        }else{
            this.roomPwd = null;
        }

        System.out.println("방 생성 성공");
        Room.roomList.add(this);
    }


    /* roomList 에서 인자로 들어온 room 객체 찾아서 반환하는 메소드 */
    public Room getEqualRoom(Room room){

        Room tempRoom = null;

        for(Room tmp : Room.roomList){
            if(tmp.getRoomNumber() == room.getRoomNumber()){
                tempRoom = tmp;
            }
        }

        if(tempRoom == null){
            System.out.println("찾는 Room 없음");
            return null;
        }

        return tempRoom;
    }

    public static ArrayList getRoomList(){
        return Room.roomList;
    }

    public boolean isLocked(){
        return this.isLock;
    }

    public int getTotalUserNumber(){
        return this.totalUserNumber;
    }

    public int getRoomNumber(){
        return this.roomNumber;
    }

    public String getRoomName(){
        return this.roomName;
    }

    public String getRoomPwd(){
        return this.roomPwd;
    }

    public User getRoomMaster(){
        return this.roomMaster;
    }

    public ArrayList getRoomIntoUser(){
        return userList;
    }

}
