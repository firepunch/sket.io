package sket.controllers;

import sket.model.action.FBConnection;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by firepunch on 2017-04-06.
 */

public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String code = "";

    public LoginController() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String act = req.getParameter("loginBtn");

        if (act == null) {
//            no button has been selected
        } else if (act.equals("fb")) {
            FBConnection fbConnection = new FBConnection();
            code = fbConnection.getFBAuthUrl();

            if (code == null || code.equals("")) {
                throw new RuntimeException(
                        "ERROR: Didn't get code parameter in callback.");
            }

            String accessToken = fbConnection.getAccessToken(code);
            ServletOutputStream out = res.getOutputStream();

            out.println("<h1>Facebook Login using Java</h1>");
            out.println("<h2>Application Main Menu</h2>");
            out.println("<div>Your Token: " + accessToken);
        } else if (act.equals("google")) {
            System.out.println("google");
//            LoginGoogle.loginGoogle = new LoginGoogle();
        } else {
//            someone has altered the HTML and sent a different value!
        }
    }
}