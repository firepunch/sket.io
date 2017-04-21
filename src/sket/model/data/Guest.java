package sket.model.data;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by KwonJH on 2017-04-12.
 */
public class Guest {

    public static ArrayList<Guest> guestList = new ArrayList<>();
    public static int countId = 0;

    private int tempId;
    private final int MIN_ARANGE = 100;
    private final int MAX_ARANGE = 999;

    public Guest() {
        allocateId();
        guestList.add(this);
    }

    public int getTempId(){
        return this.tempId;
    }

    private synchronized void allocateId(){
        this.tempId = Guest.countId;
        countId++;
    }
}
