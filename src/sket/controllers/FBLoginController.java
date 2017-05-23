package sket.controllers;

import org.json.JSONObject;
import sket.db.DBConnection;
import sket.model.action.OauthLogin;
import sket.model.data.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

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
        DBConnection db = new DBConnection();
        OauthLogin oauthLogin = new OauthLogin();

        JSONObject rcvJson = oauthLogin.getRcvJson(req, "user");

        String id = rcvJson.getString("id");
        String name = rcvJson.getString("name");
        String picture = rcvJson.getJSONObject("picture").getJSONObject("data").getString("url");
        String token = rcvJson.getString("accessToken");
        String totalExp, curExp, limitExp, level, nick = "null";

        sendJson.put("type", "facebook");
        sendJson.put("id", id);
        sendJson.put("name", name);
        sendJson.put("nick", nick);
        sendJson.put("picture", picture);

        req.setCharacterEncoding("euc-kr");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        HttpSession session = req.getSession(false);
        System.out.println(session);
        if (session == null) {
            session = req.getSession();
            sendJson.put("limitExp", 0); // 임시데이터
            session.setAttribute("user", new User(id, nick, level, totalExp, curExp));

            System.out.println(session);

            PrintWriter out = resp.getWriter();
            out.print(sendJson);
            out.flush();

            System.out.println("log : " + "FB 새로운 세션 생성");
        } else {
            System.out.println("log : " + "FB 세션 이미 있음");

            session.invalidate();
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