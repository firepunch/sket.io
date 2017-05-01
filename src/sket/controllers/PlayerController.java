package sket.controllers;

import org.json.JSONObject;
import sket.model.action.PlayerAction;
import sket.model.action.RoomAction;
import sket.model.data.Player;
import sket.model.data.Room;

import javax.servlet.http.HttpServlet;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerController extends HttpServlet {
    public PlayerController() {
        super();
    }

    public static String gameReadyToJSON(int roomId, boolean isReady, Session session) throws IOException {
        Room room = RoomAction.findRoomById(roomId);

        if (room != null) {
            Player player = PlayerAction.getPlayerEqualSession(session);
            player.setReady(isReady);
            String readyJSON = readyToPlayerJSON(player);

            return readyJSON;
        }
        return null;
    }

    public static String checkReadyAllPlayer(Room room) {
        ArrayList<Player> playerArrayList = room.getRoomIntoPlayer(room);
        int countTotalUser = room.getTotalUserNumber();
        int tempCount = 0;

        for (Player tempPlayer : playerArrayList) {
            if (tempPlayer.isReady()) {
                tempCount += 1;
            }
        }

        if (tempCount == countTotalUser) {
            return readyToAllPlayerJSON(room);
        }
        return null;
    }

    private static String readyToAllPlayerJSON(Room room) {
        JSONObject message = new JSONObject();
        message.put("type", "readyAllPlayer");
        message.put("roomId", room.getRoomId());
        message.put("ready", true);
        return message.toString();
    }

    public static String readyToPlayerJSON(Player player) {
        JSONObject message = new JSONObject();
        message.put("type", "playerReady");
        message.put("id", player.getId());
        message.put("ready", player.isReady());
        return message.toString();
    }
}