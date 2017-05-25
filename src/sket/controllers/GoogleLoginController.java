package sket.controllers;

import org.json.JSONObject;
import sket.db.DBConnection;
import sket.model.action.OauthLogin;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by firepunch on 2017-04-06.
 */

public class GoogleLoginController extends HttpServlet {
    public GoogleLoginController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject sendJson = new JSONObject();
        JSONObject rcvJson;
        DBConnection db = new DBConnection();
        OauthLogin oauthLogin = new OauthLogin();

        rcvJson = oauthLogin.getRcvJson(req, "user");
        String token = rcvJson.getJSONObject("tokenObj").getString("access_token");

        rcvJson = rcvJson.getJSONObject("profileObj");
        String id = rcvJson.getString("googleId");
        String name = rcvJson.getString("name");
        String picture = rcvJson.getString("imageUrl");
        String nick = "null";

        sendJson.put("type", "google");
        sendJson.put("id", id);
        sendJson.put("name", name);
        sendJson.put("nick", nick);
        sendJson.put("picture", picture);

        req.setCharacterEncoding("euc-kr");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        HttpSession session = req.getSession(false);
//        HttpSession session = req.getSession();
        System.out.println(session);
        if (session == null) {
            session = req.getSession();
//            session.setAttribute("user", new Player(id, token, nick, name, false));

            PrintWriter out = resp.getWriter();
            out.print(sendJson);
            out.flush();

            System.out.println("log : " + "FB 새로운 세션 생성");
        } else {
            System.out.println("log : " + "FB 세션 이미 있음");

            long sTime = session.getCreationTime();
            long eTime = session.getLastAccessedTime();
            Date sd = new Date(sTime);
            Date ed = new Date(eTime);
            sd.toLocaleString();
            ed.toLocaleString();
            System.out.println("처음 접속시간 : " + sd.toLocaleString() + "<br/>");
            System.out.println("마지막 접근시간 : " + ed.toLocaleString() + "<br/>");
        }

//        try {
//            db.InsertUser(id, nick, name);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}