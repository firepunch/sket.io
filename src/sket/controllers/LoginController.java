package sket.controllers;

import sket.db.DBConnection;
import sket.model.action.FBConnection;
import sket.model.action.GoogleConnection;
import sket.model.action.ServletCallbackSample;

import javax.servlet.ServletException;
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
        DBConnection db = new DBConnection();
        String act = req.getParameter("loginBtn");
        String accessToken, nick = null;
        // TODO: nick값 받기

        if (act == null) {
//            no button has been selected
        } else if (act.equals("fb")) {
            FBConnection fbConnection = new FBConnection();
            code = fbConnection.getFBAuthUrl();

            if (code == null || code.equals("")) {
                throw new RuntimeException(
                        "ERROR: Didn't get code parameter in callback.");
            }

            accessToken = fbConnection.getAccessToken(code);
            db.InsertUser(accessToken, nick);
        } else if (act.equals("google")) {
            GoogleConnection googleConnection = new GoogleConnection();
            ServletCallbackSample servletCallbackSample = new ServletCallbackSample();
        } else {
//            someone has altered the HTML and sent a different value!
        }
    }
}