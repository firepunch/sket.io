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

    /* 게임 준비 처리해서 json 으로 반환 */
    public static String gameReadyToJSON(int roomId, boolean isReady, String sessionID) throws IOException {
        Room room = RoomAction.findRoomById(roomId);

        if (room != null) {
            Player player = PlayerAction.getPlayerEqualSessionID(sessionID);
            player.setReady(isReady);
            String readyJSON = readyToPlayerJSON(player);

            return readyJSON;
        }
        return null;
    }

    /* 전체 플레이어가 준비 했는지 확인 후 json 반화 */
    public static String checkReadyAllPlayer(Room room) {
        ArrayList<Player> playerArrayList = room.getRoomIntoPlayer(room);
        int countTotalUser = room.getTotalUserNumber();

        System.out.println("log : checkReadyAllPlayer() : countTotalUser = " + countTotalUser);
        int tempCount = 0;

        for (Player tempPlayer : playerArrayList) {
            if (tempPlayer.isReady()) {
                tempCount += 1;
            }
        }

        System.out.println("log : checkReadyAllPlayer() : tempCount = " + tempCount);
        // 방장까지 합쳐서 계산해야 한다.
        if (tempCount + 1 == countTotalUser) {
            return readyToAllPlayerJSON(room);
        }
        return null;
    }

    /* 전체 플레이어 레디 확인 후 준비 안 한 사람이 있을 시에 보내는 JSON */
    public static String noReadyAllPlayerJSON(Room room) {
        JSONObject message = new JSONObject();
        message.put("type", "NO_READY_ALL_PLAYER");

        JSONObject data = new JSONObject();
        data.put("roomId", room.getRoomId());
        data.put("ready", false);

        message.put("data", data);

        return message.toString();
    }

    /* checkReadyAllPlayer() 메소드를 위한 json 반환 메소드 */
    private static String readyToAllPlayerJSON(Room room) {
        JSONObject message = new JSONObject();
        message.put("type", "GAME_START");
        return message.toString();
    }

    /* gameReadyToJSON() 메소드를 위한 json 으로 반환 메소드 */
    public static String readyToPlayerJSON(Player player) {
        JSONObject message = new JSONObject();
        message.put("type", "PLAYER_READY");

        JSONObject data = new JSONObject();
        data.put("id", player.getId());
        data.put("ready", player.isReady());

        message.put("data", data);
        return message.toString();
    }
}