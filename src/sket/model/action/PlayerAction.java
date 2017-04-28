package sket.model.action;

import sket.model.data.Player;

import javax.websocket.Session;

/**
 * Created by KwonJH on 2017-04-28.
 */
public class PlayerAction {

    public static Player getPlayerEqualSession(Session session) {
        for (Player tmp : Player.playerArrayList) {
            if (tmp.getSession().equals(session)) {
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

    public static Player getEqualPlayerId(String id) {
        for (Player tmp : Player.playerArrayList) {
            if (tmp.getId().equals(id)) {
                return tmp;
            }
        }
        return null;
    }
}
