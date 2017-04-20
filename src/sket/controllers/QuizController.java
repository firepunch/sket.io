package sket.controllers;

import org.json.JSONObject;
import sket.model.data.Player;

import javax.servlet.http.HttpServlet;

public class QuizController extends HttpServlet {
    public QuizController() {
        super();
    }

    public static String correctAnswer(String correctId, String examinerId, int playerScore) {
        Player targetPlayer = Player.getEqualPlayerId(correctId);
        Player examinerPlayer = Player.getEqualPlayerId(examinerId);

        targetPlayer.setPlayerScore(playerScore);
        String message = correctAnswerByJSON(targetPlayer, examinerPlayer, playerScore);

        if(message != null){
            return message;
        }else{
            return null;
        }
    }

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
}