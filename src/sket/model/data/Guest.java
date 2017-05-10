package sket.model.data;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by KwonJH on 2017-04-12.
 */
public class Guest {

    public static ArrayList<Guest> guestList = new ArrayList<>();
    public static int countId = 0;

    private final int MIN_ARANGE = 100;

    public Guest(Player player) {
        allocateId();
        guestList.add(this);
    }

    public void guestInit(){
        countId = MIN_ARANGE;
    }

    private synchronized void allocateId(){
        countId++;
    }
}
