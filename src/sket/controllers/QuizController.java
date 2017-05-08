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

    public static String sendQuizByJSON() {
        String quiz = null;

        JSONObject message = new JSONObject();
        message.put("type", "randomQuiz");
        message.put("quiz", quiz);

        return message.toString();
    }

    /* 문제 맞춘 사람, 출제자, score 작업해서 json 으로 반환 */
    public static String correctAnswer(String correctId, String examinerId, int playerScore) {
        Player targetPlayer = PlayerAction.getEqualPlayerId(correctId);
        Player examinerPlayer = PlayerAction.getEqualPlayerId(examinerId);

        targetPlayer.setPlayerScore(playerScore);
        String message = correctAnswerByJSON(targetPlayer, examinerPlayer, playerScore);

        if (message != null) {
            return message;
        } else {
            return null;
        }
    }

    /* correctAnswer 메소드를 위한 json 반환 메소드 */
    private static String correctAnswerByJSON(Player correctP, Player examinerP, int score) {
        correctP.setExaminer(true);
        examinerP.setExaminer(false);

        JSONObject message = new JSONObject();
        message.put("type", "correctAnswer");
        message.put("correcterId", correctP.getId());
        message.put("examinerId", examinerP.getId());
        message.put("score", score);

        return message.toString();
    }

    public static String sendCanvasData() {
        String data = null;

        JSONObject message = new JSONObject();
        message.put("type", "canvasData");
        message.put("data", data);
        // TODO: data 받고 JS의 redraw()실행

        return message.toString();
    }
}