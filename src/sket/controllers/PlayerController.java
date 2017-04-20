package sket.controllers;

import org.json.JSONObject;
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

    public static String gameReady(int roomId, boolean isReady, Session session) throws IOException {
        Room room = RoomController.findRoomById(roomId);

        if (room != null) {
            Player player = Player.getPlayerEqualSession(session);
            player.setReady(isReady);
            String readyJSON = Player.readyToPlayerJSON(player);

            /*
            session.getBasicRemote().sendText(readyJSON);

            String readyAllJSON = checkReadyAllPlayer(room);
            if (readyAllJSON != null){
                session.getBasicRemote().sendText(readyAllJSON);
            }
            */
            return readyJSON;
        }
        return null;
    }

    public static String checkReadyAllPlayer(Room room) {
        ArrayList<Player> playerArrayList = room.getRoomIntoPlayer();
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
}