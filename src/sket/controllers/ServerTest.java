package sket.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by hojak on 2017-04-04.
 */
public class ServerTest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("euc-kr");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        System.out.println("ServerTest 실행");
        HttpSession session = req.getSession();
        session.setAttribute("test", "test");
        resp.sendRedirect("index.html");
    }
}
