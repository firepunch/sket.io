package sket.model.data;

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
    private int userMax = 4;
    private static int countRoomId = 0;

    private boolean isLock = false;
    private int totalUserNumber = 0;
    private int roomId;
    private String roomName;
    private String roomPwd;
    private String answer;
    private Player roomMaster;
    private int timeLimit = 0;
    private boolean playingGame = false;
    private int roundLimit = 0;
    private int curRound = 0;

    public Room(String name, Player roomMaster, int roomId, boolean isLock, String pwd, int userMax, int timeLimit, int roundLimit) {
        this.roomName = name;
        this.roomMaster = roomMaster;
        this.roomId = roomId;
        this.isLock = isLock;
        this.userMax = userMax;
        this.roundLimit = roundLimit;
        this.timeLimit = timeLimit;

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
        this.totalUserNumber += 1;

        playerList.add(roomMaster);
    }
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getUserMax() {
        return userMax;
    }

    public void setUserMax(int userMax) {
        this.userMax = userMax;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getRoundLimit() {
        return roundLimit;
    }

    public int getCurRound() {
        return curRound;
    }

    public void addCurRound() {
        this.curRound += 1;
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

    public void setRoomMaster(Player roomMaster) {
        this.roomMaster.setMaster(false);
        this.roomMaster = roomMaster;
        this.roomMaster.setMaster(true);
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
                break;
            }
        }
    }

    public boolean isPlayingGame() {
        return playingGame;
    }

    public void setPlayingGame(boolean playingGame) {
        this.playingGame = playingGame;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }
}