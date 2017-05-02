package sket.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hojak on 2017-04-04.
 */
public class ServerTest extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("euc-kr");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        String password = req.getParameter("pwd");

        System.out.println("id");
    }
}
