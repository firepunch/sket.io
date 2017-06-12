package sket.controllers;

import org.json.JSONObject;
import sket.model.data.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
*
* Created by KwonJH on 2017-05-14.
*/


public class GuestController extends HttpServlet {

    private String id;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("euc-kr");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        JSONObject message = new JSONObject();
        message.put("type", "LOGIN_GUEST");
        try {
            User user = new User(true);
            message.put("id", user.getId());
            message.put("nick", user.getNick());
            message.put("picture", "null");
            message.put("level", user.getLevel());
            message.put("totalExp", user.getTotalExp());
            message.put("limitExp", user.getLimitExp());
            message.put("isGuest", true);

            System.out.println("log : " + "게스트 로그인 성공!");

            PrintWriter out = resp.getWriter();
            out.print(message);
            out.flush();

        } catch (Exception e) {
            System.out.println("log : " + "GuestController 오류");
            e.printStackTrace();
        }
    }
}
