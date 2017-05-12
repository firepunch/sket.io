package sket.model.data;

import javax.websocket.Session;
import java.util.ArrayList;

/**
 * Created by KwonJH on 2017-04-13.
 */
public class Player {
    public static ArrayList<Player> playerArrayList = new ArrayList<>();

    private String id;
    private boolean roomMaster = false;
    private boolean examiner = false;
    private Session session;
    private boolean isReady = false;
    private int playerScore = 0;
    private boolean isGuest = false;

    public Player(String id, boolean roomMaster, Session session, boolean isGuest) {
        this.id = id;
        this.roomMaster = roomMaster;
        this.session = session;
        this.isGuest = isGuest;
        playerArrayList.add(this);
    }

    public void setExaminer(boolean isExaminer){
        this.examiner = isExaminer;
    }

    public boolean isExaminer() {
        return this.examiner;
    }

    public void setPlayerScore(int score) {
        this.playerScore = score;
    }

    public long getPlayerScore() {
        return this.playerScore;
    }

    public boolean isReady() {
        return this.isReady;
    }

    public void setReady(boolean ready) {
        this.isReady = ready;
    }

    public void setRoomMaster(boolean roomMaster) {
        this.roomMaster = roomMaster;
    }

    public boolean isRoomMaster() {
        return this.roomMaster;
    }

    public String getId() {
        return this.id;
    }

    public Session getSession() {
        return this.session;
    }

    public static ArrayList<Player> getPlayerList() {
        return Player.playerArrayList;
    }
}
