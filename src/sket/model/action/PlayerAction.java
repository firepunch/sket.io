package sket.model.action;

import sket.model.data.Player;

import javax.jms.Session;

import static sket.model.data.Player.playerArrayList;

/**
 * Created by KwonJH on 2017-04-28.
 */
public class PlayerAction {

    /* 플레이어 목록 중 session 과 같은 session 을 가지고 있는 플레이어 객체 반환 */
    public static Player getPlayerEqualSessionID(String sessionID) {
        for (Player tmpPlayer : Player.playerArrayList) {
            if (tmpPlayer.getSessionID().equals(sessionID)) {
                return tmpPlayer;
            }
        }
        return null;
    }

    public static Player getPlayerEqualSession(Session session) {
        for (Player tmp : playerArrayList) {
            if (tmp.getSessionID().equals(session)) {
                return tmp;
            }
        }
        return null;
    }

    /* 플레이어 목록 중 인자와 같은 객체를 반환 */
    public static Player getEqualPlayer(Player player) {
        for (Player tmp : Player.playerArrayList) {
            if (tmp.getSessionID().equals(player.getSessionID())) {
                return tmp;
            }
        }
        return null;
    }

    /* 플레이어 목록 중 인자와 같은 id 를 가진 플레이어 객체 반화 */
    public static Player getEqualPlayerId(String id) {
        for (Player tmp : playerArrayList) {
            if (tmp.getId().equals(id)) {
                return tmp;
            }
        }
        return null;
    }
}
