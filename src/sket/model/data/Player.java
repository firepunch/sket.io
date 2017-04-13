package sket.model.data;

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

    public Player(String id, boolean roomMaster, Session session) {
        this.id = id;
        this.roomMaster = roomMaster;
        this.session = session;
    }


    public static void addPlayerToList(Player player){
        playerArrayList.add(player);
    }

    public void setRoomMaster(boolean roomMaster){
        this.roomMaster = roomMaster;
    }

    public String getId() {
        return this.id;
    }

    public boolean isRoomMaster() {
        return this.roomMaster;
    }

    public Session getSession() {
        return this.session;
    }

    public static ArrayList<Player> getPlayerList() {
        return Player.playerArrayList;
    }

    public static Player getEqualPlayer(Player player) {
        for(Player tmp : Player.getPlayerList()){
            if(tmp.getSession().equals(player.getSession())){
                return tmp;
            }
        }
        return null;
    }

    public static Player getPlayerEqualSession(Session session){
        for (Player tmp : Player.getPlayerList()){
            if(tmp.getSession().equals(session)){
                return tmp;
            }
        }
        return null;
    }
}
