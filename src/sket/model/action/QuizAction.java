package sket.model.action;

import sket.model.data.Player;
import sket.model.data.Room;

import java.util.ArrayList;

/**
 * Created by KwonJH on 2017-05-02.
 */

public class QuizAction {
    private static Room targetRoom = null;

    public QuizAction(Room targetRoom) {
        this.targetRoom = targetRoom;
    }

    /* 방에서 출제자를 제외한 나머지 플레이어를 반환 */
/*
    TODO: ㅇㅁㄹ, sessionid 받는 것으로 수정
    public static ArrayList<Session> excludeExaminerSession(String id) {
        ArrayList<Session> sessionArrayList = new ArrayList<>();
        for (Player player : Room.getRoomIntoPlayer(targetRoom)) {
            if (player.getId() != id) {
                sessionArrayList.add(player.getSession());
            }
        }
        return sessionArrayList;
    }
*/
    public static ArrayList<String> excludeExaminerSession(String id) {
        ArrayList<String> sessionArrayList = new ArrayList<>();
        for (Player player : Room.getRoomIntoPlayer(targetRoom)) {
            if (player.getId() != id) {
                sessionArrayList.add(player.getSessionId());
            }
        }
        return sessionArrayList;
    }
}
