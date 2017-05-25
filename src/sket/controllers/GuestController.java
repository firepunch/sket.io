package sket.controllers;

import sket.model.action.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
*
* Created by KwonJH on 2017-05-14.
*/


public class GuestController extends HttpServlet {

    private String id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("euc-kr");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");

        try {
            HttpSession session = req.getSession();
//            TODO: ㅇㅁㄹ, 아이디랑 닉넴
//            session.setAttribute("user", new User(null, null, null, true));
            SessionManager.addSession(session);

            resp.sendRedirect("/test/test.html");

            System.out.println("log : "+"게스트 로그인 성공!");
        }catch (Exception e){
            System.out.println("log : "+ "GuestController 오류");
            e.printStackTrace();
        }
    }
}
