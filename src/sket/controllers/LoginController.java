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

            String graph = fbConnection.getFbGraph(accessToken);
            Map<String, String> fbProfileData = fbConnection.getGrapthData(graph);

            System.out.println("FB--------\n\nNmae: " + fbProfileData.get("first_name"));
        } else if (act.equals("google")) {
            GoogleConnection googleConnection = new GoogleConnection();

            // 엑세스 권한을 부여할 수 있는 권한 부여 페이지로 이동
            String url = flow.newAuthorizationUrl().setState("xyz")
                    .setRedirectUri("https://client.example.com/rd").build();
            res.sendRedirect(url);

            // Get the OAuth2 credential.
            googleConnection.getAccessToken("userid");
        } else {
//            someone has altered the HTML and sent a different value!
        }
    }
}