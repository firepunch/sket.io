package sket.controllers;

import org.json.JSONObject;
import sket.model.action.PlayerAction;
import sket.model.data.Player;

import javax.servlet.http.HttpServlet;

public class QuizController extends HttpServlet {
    public QuizController() {
        super();
    }

    /* 랜덤 퀴즈를 json 으로 반환하는 메소드 */
    public static JSONObject sendQuizByJSON() {
        String quiz = null;

        JSONObject message = new JSONObject();
        JSONObject data = new JSONObject();
        message.put("type", "RANDOM_QUIZ");
        data.put("quiz", quiz);
        message.put("data", data);

        return message;
    }

    /* 문제 맞춘 시간에 따라 점수 측정 */
    public static int getScore(int total, int cur) {
        // 한 문제당 100점
        int addScore = total / cur * 100;

        return addScore;
    }

    /* 문제 맞춘 사람 점수 더해줌 */
    public static void addScore(String correctId, int addScore) {
        Player targetPlayer = PlayerAction.getEqualPlayerId(correctId);

        targetPlayer.addScore(addScore);
    }

    /* 전체 점수 감점 */
    public static void minusScore(int roomId, int minusScore) {
        // TODO 부탁쓰~~~ 모든 룸 멤버의 점수 감점 매소드는 만들었음
        // ~~.minusScore(minusScore);
    }
}