package sket.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sket.model.action.Authenticator;
import sket.model.data.User;

import sun.text.normalizer.ICUBinary.Authenticate;

public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginController() {
        super();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nick = request.getParameter("nick");
        RequestDispatcher rd = null;

        Authenticator authenticator = new Authenticator();
        String result = authenticator.authenticate(username, password);
        if (result.equals("success")) {
//            rd = request.getRequestDispatcher("/success.jsp");
            System.out.println("success login");
            User user = new User(username, password, nick);
            request.setAttribute("user", user);
        } else {
            System.out.println("success fail");
//            rd = request.getRequestDispatcher("/error.jsp");
        }
        rd.forward(request, response);
    }

}