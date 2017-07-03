package sket.model.data;

import java.util.ArrayList;

/**
 * Created on 2017-04-13.
 */
public class Player {
    public static ArrayList<Player> playerArrayList = new ArrayList<>();

    private String id; // guest는 100부터 or oauthID
    private String nickname;
    private String picture;
    private String sessionID;

    private int score = 0;
    private boolean isMaster = false;
    private boolean isExaminer = false;
    private boolean isReady = false;
    private boolean isGuest = false;
    private boolean inRoom = false;

    /* Guest 일 시 User 에서 게스트 아이디 처리했음 */
    public Player(String id, String nickname, String picture, String sessionID, boolean isGuest) {
        this.id = id;
        this.nickname = nickname;
        this.picture = picture;
        this.sessionID = sessionID;
        this.isGuest = isGuest;

        playerArrayList.add(this);
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getPlayerLevel() {
        for (User user : User.getUserList()) {
            if (user.getId().equals(this.getId())) {
                return user.getLevel();
            }
        }
        return 0;
    }

    public void setPlayerLevel(int level) {
        for (User user : User.getUserList()) {
            if (user.getId().equals(this.getId())) {
                user.setLevel(level);
            }
        }
    }

    public boolean isInRoom() {
        return inRoom;
    }

    public void setInRoom(boolean inRoom) {
        this.inRoom = inRoom;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public static ArrayList<Player> getPlayerArrayList() {
        return playerArrayList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void minusScore(int score) {
        this.score -= score;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean master) {
        isMaster = master;
    }

    public boolean isExaminer() {
        return isExaminer;
    }

    public void setExaminer(boolean examiner) {
        isExaminer = examiner;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }
}
