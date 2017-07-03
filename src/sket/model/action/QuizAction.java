package sket.model.action;

import org.json.JSONObject;
import sket.controllers.QuizController;
import sket.model.data.Player;
import sket.model.data.Room;

import java.text.SimpleDateFormat;
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

    /* 채팅 정답 비교 */
    private boolean compareAnswer(String msg) {
        if (msg.equals(targetRoom.getAnswer())) {
            if (targetRoom.getCurRound() == targetRoom.getRoundLimit()) {
                // 라운드 종료
                //sendMessageToRoomMembers();
            }
            return true;
        } else {
            return false;
        }
    }

    /* Response chat json 생성 */
    public JSONObject makeRepJson(JSONObject jsonObject, String msg, String senderId, int addScore) {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        boolean correct = compareAnswer(msg);
        if (correct) { // 문제를 맞히면
            QuizController.addScore(senderId, addScore);
            jsonObject.getJSONObject("data").put("correct", correct);
            jsonObject.getJSONObject("data").put("score", addScore);
        } else {
            jsonObject.getJSONObject("data").put("correct", correct);
        }
        jsonObject.getJSONObject("data").put("time", sdf.format(date));

        return jsonObject;
    }
}
