package sket.controllers;

import org.json.JSONObject;
import sket.db.DBConnection;
import sket.model.action.OauthLogin;
import sket.model.action.SessionManager;
import sket.model.data.User;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by firepunch on 2017-04-06.
 */

public class FBLoginController extends HttpServlet {
    public FBLoginController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBConnection db = new DBConnection();
        OauthLogin oauthLogin = new OauthLogin();
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession();
        JSONObject sendJson = oauthLogin.getRcvJson(req, "facebook", "user");

        req.setCharacterEncoding("euc-kr");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        try {
            sendJson = db.selectUser(sendJson.getString("id"), "facebook");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String id = sendJson.getString("id");
//        String nick = sendJson.getString("nick");
        String nick = "null";
        if (sendJson.getString("id").equals("null")) {
            session.setAttribute("user", new User(id, nick));
            SessionManager.addSession(session);
            System.out.println("log : " + "FB 새로운 세션, 신규회원 생성");

            sendJson.put("level", 1);
            sendJson.put("limitExp", 300);
            sendJson.put("totalExp", 0);
            sendJson.put("curExp", 0);

            try {
                db.insertUser(id, nick);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException("oauth login insert error " + e);
            }
        } else if (SessionManager.getUserIdEqualSession(session) == null) {
            System.out.println("log : fb 기존회원 새로운 세션 생성");

            session.setAttribute("user", new User(id, nick,
                    sendJson.getInt("level"), sendJson.getInt("limitExp"),
                    sendJson.getInt("totalExp"), sendJson.getInt("curExp")));
            SessionManager.addSession(session);
        }

        out.print(sendJson);
        out.flush();

//            로그아웃
//            session.invalidate();
    }
}