package sket.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import sket.db.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

/**
 * Created by firepunch on 2017-04-06.
 */

public class GoogleLoginController extends HttpServlet {
    public GoogleLoginController() {
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
            System.out.println("AAA "+rcvJson);
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
        String token = rcvJson.getJSONObject("tokenObj").getString("access_token");

        rcvJson = rcvJson.getJSONObject("profileObj");
        String id = rcvJson.getString("googleId");
        String name = rcvJson.getString("name");
        String picture = rcvJson.getString("imageUrl");
        String nick = "null";

        System.out.println("ASDASD   "+token + id + name + picture);


        sendJson.put("type", "google");
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