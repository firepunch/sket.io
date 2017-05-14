package sket.model.data;

import org.json.JSONObject;

import javax.websocket.Session;
import java.util.ArrayList;

/**
 * Created by KwonJH on 2017-04-12.
 */
public class Guest {

    private static ArrayList<Guest> guestList = new ArrayList<>();
    private static int countId = 0;

    private Player player;
    private final int MIN_ARANGE = 100;

    /* Guest 접속 했을 시, 방장 여부, session, guest 인지 인자로 받아 Player 인스턴스 생성 */
    public Guest(boolean roomMaster, Session session) {

        // guestList 의 사이즈가 0 일 경우 guestInit() 메소드 호출
        if (guestList.size() == 0) {
            guestInit();
        }

        player = new Player(Integer.toString(countId), roomMaster, session, true);
        allocateId();
        guestList.add(this);
    }

    public void setExaminer(boolean isExaminer) {
        player.setExaminer(isExaminer);
    }

    public void setReady(boolean isReady) {
        player.setReady(isReady);
    }

    public boolean isExaminer() {
        return player.isExaminer();
    }

    public boolean isReady() {
        return player.isReady();
    }

    public void guestInit() {
        countId = MIN_ARANGE;
        guestList.clear();
    }

    public Session getSession() {
        return player.getSession();
    }

    public void setRoomMaster(boolean roomMaster) {
        player.setRoomMaster(roomMaster);
    }

    public String getId() {
        return player.getId();
    }

    private synchronized void allocateId() {
        countId += 1;
    }

    public static ArrayList<Guest> getGuestList() {
        return guestList;
    }

    public boolean deleteGuest(Session session) {
        boolean isSuccess = false;

        for (Guest tempGuest : guestList) {
            if (tempGuest.getSession() == session) {
                guestList.remove(tempGuest);
                player = null;
                isSuccess = true;
            }
        }
        return isSuccess;
    }
}
