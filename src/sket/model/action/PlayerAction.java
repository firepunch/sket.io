package sket.model.action;

import sket.model.data.Player;

import javax.websocket.Session;

/**
 * Created by KwonJH on 2017-04-28.
 */
public class PlayerAction {

    /* 플레이어 목록 중 session 과 같은 session 을 가지고 있는 플레이어 객체 반환 */
    public static Player getPlayerEqualSession(Session session) {
        for (Player tmp : Player.playerArrayList) {
            if (tmp.getSession().equals(session)) {
                return tmp;
            }
        }
        return null;
    }

    /* 플레이어 목록 중 인자와 같은 객체를 반환 */
    public static Player getEqualPlayer(Player player) {
        for (Player tmp : Player.playerArrayList) {
            if (tmp.getSession().equals(player.getSession())) {
                return tmp;
            }
        }
        return null;
    }

    /* 플레이어 목록 중 인자와 같은 id 를 가진 플레이어 객체 반화 */
    public static Player getEqualPlayerId(String id) {
        for (Player tmp : Player.playerArrayList) {
            if (tmp.getId().equals(id)) {
                return tmp;
            }
        }
        return null;
    }

}
