package sket.controllers;

import org.json.JSONObject;
import sket.model.action.ParseQuiz;
import sket.model.data.Player;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuizController extends HttpServlet {
    public QuizController() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String act = req.getParameter("makeBtn");

        if (act == null) {
//            no button has been selected
        } else if (act.equals("parseQuiz")) {
            String krdictUrl = "https://krdict.korean.go.kr/dicSearch/senseCategory?commentReturnUrl=&ParaWordNo=&deleteWord_no=&currentPage=1&blockCount=undefined&categoryName=%EC%8B%9D%EC%83%9D%ED%99%9C+%3E+%EC%9D%8C%EC%8B%9D&searchFlag=Y&downloadInfo=&downloadInfoText=&downloadGubun=&downloadType=&downloadItemList=&downloadMultilanList=&priMoveUrl=&lgCategoryCode=3&miCategoryCode=1032";
            ParseQuiz parseQuiz = new ParseQuiz();
            parseQuiz.getWord(krdictUrl);
        }
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