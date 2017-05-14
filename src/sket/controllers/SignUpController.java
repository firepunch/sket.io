package sket.controllers;

import sket.model.action.SessionManager;
import sket.model.data.Player;
import sket.model.data.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by hojak on 2017-04-04.
 */
public class SignUpController extends HttpServlet {

    private String id;
    private String password;
    private String nickname;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("euc-kr");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");

        id = req.getParameter("id");
        password = req.getParameter("password");
        nickname = req.getParameter("nick");

        HttpSession session = req.getSession();
        session.setAttribute("user", new User(id, password, nickname, false));
        SessionManager.addSession(session);

        resp.sendRedirect("/test/test.html");
    }
}

