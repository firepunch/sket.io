package sket.controllers;

import org.json.JSONObject;
import sket.model.action.RoomAction;
import sket.model.data.Player;
import sket.model.data.Room;

import javax.servlet.http.HttpServlet;
import java.util.ArrayList;

public class GameController extends HttpServlet {

    public GameController() {
        super();
    }

    /* 랜덤 출제자를 json 으로 반환 */
    public static String randomExaminerToJSON(int roomId) {

        Room targetRoom = RoomAction.findRoomById(roomId);
        int countUser = targetRoom.getTotalUserNumber();

        int randValue = (int) (Math.random() * countUser) + 1;
        ArrayList<Player> roomMembers = targetRoom.getPlayerList();

        Player targetPlayer = roomMembers.get(randValue - 1);

        JSONObject message = new JSONObject();
        message.put("type", "RANDOM_EXAMINER");

        JSONObject data = new JSONObject();
        data.put("id", targetPlayer.getId());
        data.put("roomId", roomId);

        message.put("data", data);

        return message.toString();
    }
}