package sket.controllers;

import org.json.JSONObject;
import sket.db.DBConnection;
import sket.model.action.OauthLogin;
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
        JSONObject originJson = oauthLogin.getRcvJson(req, "FACEBOOK", "user");
        JSONObject dbJson = new JSONObject();

        req.setCharacterEncoding("euc-kr");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        String id = originJson.getString("id");
        try {
            dbJson = db.selectUser(id, "FACEBOOK", false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        String nick = originJson.getString("nick");
        String nick = "null";
        if (dbJson.getString("id").equals("null")) {
            try {
                nick = db.insertUser(id, nick);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException("oauth login insert error " + e);
            }
            originJson.put("nick", nick);
            originJson.put("level", 1);
            originJson.put("limitExp", 300);
            originJson.put("totalExp", 0);
            originJson.put("curExp", 0);
            originJson.put("isGuest", false);

            new User(id, nick);
            out.print(originJson);

            System.out.println("log : " + "FB 새로운 세션, 신규회원 생성");
        } else {
            new User(
                    id, nick,
                    originJson.getString("picture"),
                    dbJson.getInt("level"),
                    dbJson.getInt("limitExp"),
                    dbJson.getInt("totalExp"),
                    dbJson.getInt("curExp")
            );
            dbJson.put("picture", originJson.getString("picture"));
            out.print(dbJson);

            System.out.println("log : fb 기존회원 새로운 세션 생성");
        }

        out.flush();
//            로그아웃
//            session.invalidate();
    }
}