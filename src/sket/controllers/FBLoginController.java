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
    private static final long serialVersionUID = 1L;
    private String code, accessToken, nick = "";

    public FBLoginController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBConnection db = new DBConnection();
        OauthLogin oauthLogin = new OauthLogin();
        HttpSession session = req.getSession();
        JSONObject sendJson = null;
        PrintWriter out = resp.getWriter();

        JSONObject rcvJson = oauthLogin.getRcvJson(req, "user");

        String id = rcvJson.getString("id");
        String name = rcvJson.getString("name");
        String picture = rcvJson.getJSONObject("picture").getJSONObject("data").getString("url");
        String token = rcvJson.getString("accessToken");
        String nick = "null";

        req.setCharacterEncoding("euc-kr");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        try {
            sendJson = db.selectUser(id, "facebook");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (sendJson.getString("id").equals("null")) {
            session.setAttribute("user", new User(id, nick));
            SessionManager.addSession(session);

            System.out.println("sessionlist in fb\n   " + SessionManager.getSessionList());
            System.out.println("log : " + "FB 새로운 세션, 신규회원 생성");
            System.out.println("생성한 것     " + session);

            sendJson.put("type", "facebook");
            sendJson.put("id", id);
            sendJson.put("name", name);
            sendJson.put("nick", nick);
            sendJson.put("picture", picture);
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
        } else {
            if (SessionManager.getUserIdEqualSession(session) == null) {
                System.out.println("log : fb 기존회원 새로운 세션 생성");

                session.setAttribute("user", new User(id, sendJson.getString("nick"),
                        sendJson.getInt("level"), sendJson.getInt("limitExp"),
                        sendJson.getInt("totalExp"), sendJson.getInt("curExp")));
                SessionManager.addSession(session);

            } else {
                System.out.println("log : " + "FB 세션 이미 있음");

                session.getAttribute("user");
                SessionManager.addSession(session);

                // 세션id가 계속바뀜
                System.out.println("fb session id:   " + req.getSession().getId());
            }
        }

        out.print(sendJson);
        out.flush();

//            로그아웃
//            session.invalidate();
    }
}