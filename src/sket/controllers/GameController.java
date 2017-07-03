package sket.controllers;

import org.json.JSONArray;
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
        message.put("type", "SET_EXAMINER");

        JSONObject data = new JSONObject();
        data.put("id", targetPlayer.getId());
        data.put("roomId", roomId);

        message.put("data", data);

        return message.toString();
    }

    public static String setExaminerToJSON(Room targetRoom, String userId) {
        JSONObject message = new JSONObject();
        message.put("type", "SET_EXAMINER");
        JSONObject data = new JSONObject();
        data.put("id", userId);
        message.put("data", data);

        return message.toString();
    }


    public static String gameEndToJSON(Room targetRoom) {

        ArrayList<Player> playerArrayList = targetRoom.getPlayerList();

        Player temp;

        for(int q =0; q<targetRoom.getPlayerList().size() -1; ++ q) {
            for (int i = 0; i < targetRoom.getPlayerList().size() - 1; ++i) {
                if(playerArrayList.get(i).getScore() < playerArrayList.get(i+1).getScore()){
                    temp = playerArrayList.get(i);
                    playerArrayList.set(i, playerArrayList.get(i+1));
                    playerArrayList.set(i+1, temp);
                }
            }
        }

        for(Player target : playerArrayList){
            System.out.println(target.getScore());
        }

        JSONObject message = new JSONObject();
        message.put("type", "GAME_END");

        JSONObject data = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for(Player target : playerArrayList){
            JSONObject object = new JSONObject();
            object.put("id", target.getId());
            object.put("score", target.getScore());
            object.put("nick", target.getNickname());
            jsonArray.put(object);
        }

        data.put("ranking", jsonArray);
        message.put("data", data);
        return  message.toString();
    }
}