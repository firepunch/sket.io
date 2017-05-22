package sket.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import sket.db.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by firepunch on 2017-04-06.
 */

public class FBLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String code, accessToken, nick = "";

    public FBLoginController() {
        super();
    }


    public static JSONObject getRcvJson(HttpServletRequest req) throws IOException {
        JSONObject rcvJson;

        try {
            StringBuffer jb = new StringBuffer();
            String line = null;
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
            rcvJson = new JSONObject(jb.toString());
            rcvJson = rcvJson.getJSONObject("user");
        } catch (JSONException e) {
            throw new IOException("Error parsing JSON request string");
        }
        return rcvJson;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject sendJson = new JSONObject();
        JSONObject rcvJson;
        DBConnection db = new DBConnection();

        rcvJson = getRcvJson(req);

        String id = rcvJson.getString("id");
        String name = rcvJson.getString("name");
        String picture = rcvJson.getJSONObject("picture").getJSONObject("data").getString("url");
        String token = rcvJson.getString("accessToken");
        String nick = "null";

        sendJson.put("type", "facebook");
        sendJson.put("id", id);
        sendJson.put("name", name);
        sendJson.put("nick", nick);
        sendJson.put("picture", picture);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(sendJson);
        out.flush();

        try {
            db.InsertUser(id, nick, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}