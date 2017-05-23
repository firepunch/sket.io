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
import java.sql.SQLException;
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
        String nick = "null";

        req.setCharacterEncoding("euc-kr");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        HttpSession session = req.getSession(false);
        System.out.println(session);
        if (session == null) {
            try {
                sendJson.put("type", "facebook");
                sendJson.put("id", id);
                sendJson.put("name", name);
                sendJson.put("nick", nick);
                sendJson.put("picture", picture);
                sendJson.put("level", 1);
                sendJson.put("limitExp", 300);
                sendJson.put("totalExp", 0);
                sendJson.put("curExp", 0);

                db.InsertUser(id, nick);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException("oauth login insert error " + e);
            }

            session = req.getSession();
            session.setAttribute("user", new User(id, nick, 1, 0, 0));

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

//        이미 회원이라면
//        sendJson = db.SelectUser(id, "facebook");

//        try {
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}