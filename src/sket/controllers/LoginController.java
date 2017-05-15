package sket.controllers;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import sket.db.DBConnection;
import sket.model.action.FBConnection;
import sket.model.action.GoogleConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by firepunch on 2017-04-06.
 */

public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String code = "";
    private AuthorizationCodeFlow flow;

    public LoginController() {
        super();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        DBConnection db = new DBConnection();
        String act = req.getParameter("loginBtn");
        String accessToken, nick = null;
        // TODO: nick값 받기

        if (act == null) {
            // no button has been selected
        } else if (act.equals("fb")) {
            FBConnection fbConnection = new FBConnection();
            code = fbConnection.getFBAuthUrl();

            if (code == null || code.equals("")) {
                throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
            }

            accessToken = fbConnection.getAccessToken(code);
//            db.InsertUser(accessToken, nick);

            String graph = fbConnection.getFbGraph(accessToken);
            Map<String, String> fbProfileData = fbConnection.getGrapthData(graph);

            System.out.println("log: FB값 확인(id, 이름, 사진) : " + fbProfileData.get("first_name"));
        } else if (act.equals("google")) {
            GoogleConnection googleConnection = new GoogleConnection();
            code = googleConnection.getGoogleAuthUrl();

            if (code == null || code.equals("")) {
                throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
            }
            res.sendRedirect(code);

            accessToken = googleConnection.getAccessToken(code);
            System.out.println("log : 구글 토큰 : " + accessToken);
        } else {

        }
    }
}