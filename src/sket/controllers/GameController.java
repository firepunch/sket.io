package sket.controllers;

import org.json.JSONObject;

import javax.servlet.http.HttpServlet;

public class GameController extends HttpServlet {

    public GameController() {
        super();
    }

    public static String randomExaminerToJSON(String id, int roomId) {
        JSONObject message = new JSONObject();
        message.put("type", "randomExaminer");
        message.put("id", id);
        message.put("roomId", roomId);

        return message.toString();
    }

}