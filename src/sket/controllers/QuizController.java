package sket.controllers;

import sket.model.data.Player;
import sket.model.data.Room;

import javax.servlet.http.HttpServlet;
import javax.websocket.Session;

public class QuizController extends HttpServlet {
    public QuizController() {
        super();
    }

    public static void correctAnswer(int roomId, String correctId, String examinerId, int playerScore) {
        Room targetRoom = RoomController.findRoomById(roomId);
        Player targetPlayer = Player.getEqualPlayerId(correctId);
        Player examinerPlayer = Player.getEqualPlayerId(examinerId);

        targetPlayer.plusPlayerScore(playerScore);
        changeRoomMasterByJSON(targetPlayer, examinerPlayer);
    }

    private static void changeRoomMasterByJSON(Player correctP, Player examinerP) {
        correctP.setRoomMaster(true);
        examinerP.setRoomMaster(false);
    }
}