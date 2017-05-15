package sket.controllers;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import sket.db.DBConnection;
import sket.model.action.FBConnection;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Created by firepunch on 2017-04-06.
 */

public class FBLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String code = "";
    private AuthorizationCodeFlow flow;

    public FBLoginController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBConnection db = new DBConnection();

        String code, accessToken, nick = null;
        // TODO: nick값 받기

        FBConnection fbConnection = new FBConnection();

        code = req.getParameter("code");
        System.out.println("fb code : " + code);

        if (code == null || code.equals("")) {
            throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
        }

        accessToken = fbConnection.getAccessToken(code);
//            db.InsertUser(accessToken, nick);

        String graph = fbConnection.getFbGraph(accessToken);
        Map<String, String> fbProfileData = fbConnection.getGrapthData(graph);

        System.out.println("log: FB값 확인(id) : " + fbProfileData.get("id"));
        System.out.println("log: FB값 확인(이름) : " + fbProfileData.get("name"));
        resp.sendRedirect("http://localhost:8080");
        return;
    }
}