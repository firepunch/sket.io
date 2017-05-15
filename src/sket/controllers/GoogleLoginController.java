package sket.controllers;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import sket.db.DBConnection;
import sket.model.action.FBConnection;
import sket.model.action.GoogleConnection;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import static sket.Configure.url;

/**
 * Created by firepunch on 2017-04-06.
 */

public class GoogleLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String code = "";
    private AuthorizationCodeFlow flow;

    public GoogleLoginController() {
        super();
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        String code, accessToken, nick = null;

        System.out.println("google servie");
        System.out.println(req.getParameter("code"));

        System.out.println("log: google click");
        GoogleConnection googleConnection = new GoogleConnection();

//            res.sendRedirect(googleConnection.getGoogleAuthUrl());
//            req.getRequestDispatcher(googleConnection.getGoogleAuthUrl()).include(req, res);

//            String fetchedUrl = googleConnection.getFinalRedirectedUrl(googleConnection.getGoogleAuthUrl());
//            System.out.println("FetchedURL is:" + fetchedUrl);

        code = req.getParameter("code");

        System.out.println("log : 구글 인증 code : "+ req.getParameter("code"));
        System.out.println("log : 구글 인증 codeURL :"+googleConnection.getGoogleAuthUrl());

        if (code == null || code.equals("")) {
            throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
        }

        accessToken = googleConnection.getAccessToken(code);
        String graph = googleConnection.getGoogleGraph(accessToken);
        Map<String, String> gProfileData = googleConnection.getGrapthData(graph);

        System.out.println("log: 구글값 확인(id) : " + gProfileData.get("id"));
        System.out.println("log: 구글값 확인(이름) : " + gProfileData.get("name"));
        System.out.println("log: 구글값 확인(이름) : " + gProfileData.get("picture"));

        System.out.println("log : 구글 토큰 : " + accessToken);

    }
}