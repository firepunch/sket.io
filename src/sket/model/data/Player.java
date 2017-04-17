package sket.model.data;

import org.json.JSONObject;

import javax.websocket.Session;
import java.util.ArrayList;

/**
 * Created by KwonJH on 2017-04-13.
 */
public class Player {
    private static ArrayList<Player> playerArrayList = new ArrayList<>();
    private String id;
    private boolean roomMaster;
    private Session session;
    private boolean isReady = false;
    private int playerScore = 0;

    public Player(String id, boolean roomMaster, Session session) {
        this.id = id;
        this.roomMaster = roomMaster;
        this.session = session;
    }

    public void plusPlayerScore(int plusScore) {
        this.playerScore += plusScore;
    }

    public void minusPlayerScore(int minusScore) {
        if (playerScore == 0) {
            this.playerScore = 0;
        } else if (playerScore < minusScore) {
            this.playerScore = 0;
        } else {
            this.playerScore -= minusScore;
        }
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

    public static void addPlayerToList(Player player) {
        playerArrayList.add(player);
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

    public static Player getEqualPlayerId(String id) {
        for (Player tmp : Player.playerArrayList) {
            if (tmp.getId().equals(id)) {
                return tmp;
            }
        }
        return null;
    }

    public static Player getEqualPlayer(Player player) {
        for (Player tmp : Player.playerArrayList) {
            if (tmp.getSession().equals(player.getSession())) {
                return tmp;
            }
        }
        return null;
    }

    public static Player getPlayerEqualSession(Session session) {
        for (Player tmp : Player.playerArrayList) {
            if (tmp.getSession().equals(session)) {
                return tmp;
            }
        }
        return null;
    }

    public static String readyToPlayerJSON(Player player) {
        JSONObject message = new JSONObject();
        message.put("type", "playerReady");
        message.put("id", player.getId());
        message.put("ready", player.isReady());
        return message.toString();
    }
}
