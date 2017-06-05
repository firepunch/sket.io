package sket.model.action;

import sket.model.data.Player;
import sket.model.data.Room;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by KwonJH on 2017-05-02.
 */

public class QuizAction {
    private static Room targetRoom = null;

    public QuizAction(Room targetRoom) {
        this.targetRoom = targetRoom;
    }

    /* 방에서 출제자를 제외한 나머지 플레이어를 반환 */
    public static LinkedList<String> excludeExaminerSession(String id) {
        LinkedList<String> sessionArrayList = new LinkedList<>();
        for (Player player : Room.getRoomIntoPlayer(targetRoom)) {
            if (player.getId() != id) {
                sessionArrayList.add(player.getSessionID());
            }
        }
        return sessionArrayList;
    }
}
