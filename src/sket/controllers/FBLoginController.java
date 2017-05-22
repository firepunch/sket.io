package sket.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import sket.db.DBConnection;
import sket.model.action.OauthLogin;
import sket.model.action.SessionManager;
import sket.model.data.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject sendJson = new JSONObject();
        JSONObject rcvJson;
        DBConnection db = new DBConnection();
        OauthLogin oauthLogin = new OauthLogin();

        rcvJson = oauthLogin.getRcvJson(req, "user");

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

        System.out.println("FB  :  " + id+name+picture);
        req.setCharacterEncoding("euc-kr");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        try {
            HttpSession session = req.getSession();
            session.setAttribute("user", new User(id, token, nick, name, false));
            SessionManager.addSession(session);

            PrintWriter out = resp.getWriter();
            out.print(sendJson);
            out.flush();

            System.out.println("log : "+"oauth 로그인 성공!");
        }catch (Exception e){
            System.out.println("log : "+ "FBLoginController 오류");
            e.printStackTrace();
        }

//        try {
//            db.InsertUser(id, nick, name);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}