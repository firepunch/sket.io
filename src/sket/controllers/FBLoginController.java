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
        HttpSession session = req.getSession();
        JSONObject sendJson = null;
        DBConnection db = new DBConnection();
        OauthLogin oauthLogin = new OauthLogin();
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

        try {
            sendJson = db.selectUser(id, "facebook");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        if (sendJson.getString("id").equals("null")) {
        if (req.getParameter("JSESSIONID")!=null) {
            Cookie cookie = new Cookie("JSESSIONID", req.getParameter("JSESSIONID"));
            resp.addCookie(cookie);

            session.setAttribute("user", new User(id, nick));
            SessionManager.addSession(session);

            System.out.println("DDDD" + SessionManager.getSessionList());
            System.out.println("log : " + "FB 새로운 세션 생성");
            System.out.println("생성한 것     " + session);

//              보내줄 Json
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

            out.print(sendJson);
            out.flush();
        } else {
            System.out.println("log : " + "FB 세션? 정보! 이미 있음");
            String sessionId = session.getId();
            Cookie cookie = new Cookie("JSESSIONID", sessionId);
            resp.addCookie(cookie);


            int level = sendJson.getInt("level");
            int limitExp = sendJson.getInt("limitExp");
            int totalExp = sendJson.getInt("totalExp");
            int curExp = sendJson.getInt("curExp");

            session.setAttribute("user", new User(id, sendJson.getString("nick"),
                    level, limitExp, totalExp, curExp));
            SessionManager.addSession(session);

            // 세션id가 계속바뀜
            System.out.println("IDIDD   " + req.getSession().getId());

            out.print(sendJson);
            out.flush();
        }

//            로그아웃
//            session.invalidate();
    }
}