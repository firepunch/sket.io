package sket.model.data;

import java.util.ArrayList;

public class User {

    private static ArrayList<User> userList = new ArrayList<>();
    private static int allocateGuestID = 100;

    private String id = null;
    private String nick = null;
    private String picture = null;
    private int level = 1;
    private int limitExp = 300;
    private int totalExp = 0;
    private int curExp = 0;
    private boolean isGuest = false;

    /* oauth 로그인 시 생성자 */
    public User(String id, String nick, String picture, int level, int limitExp, int totalExp, int curExp) {
        this.id = id;
        this.nick = nick;
        this.picture = picture;
        this.level = level;
        this.limitExp = limitExp;
        this.totalExp = totalExp;
        this.curExp = curExp;

        System.out.println(this);
        userList.add(this);
    }

    public User(boolean isGuest) {
        if (isGuest == true) {
            this.isGuest = isGuest;
            this.id = allocateGuestID + "";
            this.nick = "Guest_" + allocateGuestID + "";

            allocateGuestID += 1;
            userList.add(this);

        }
    }

    /* 게스트 또는 신규 로그인 생성자 */
    public User(String id, String nick) {
        this.id = id;
        this.nick = nick;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isGuest() {
        return this.isGuest;
    }

    public int getLimitExp() {
        return limitExp;
    }

    public void setLimitExp(int limitExp) {
        this.limitExp = limitExp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
    }

    public int getCurExp() {
        return curExp;
    }

    public void setCurExp(int curExp) {
        this.curExp = curExp;
    }

    public static int getAllocateGuestID() {
        return allocateGuestID;
    }

    public static void setAllocateGuestID(int guestID) {
        allocateGuestID = guestID;
    }

    public static ArrayList<User> getUserList() {
        return userList;
    }

    public static void addUser(User user) {
        userList.add(user);
    }
}