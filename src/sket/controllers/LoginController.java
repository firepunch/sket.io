package sket.controllers;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import sket.db.DBConnection;
import sket.model.action.FBConnection;
import sket.model.action.GoogleConnection;

import javax.servlet.ServletException;
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
        String act = req.getRequestURI();
        String code, accessToken, nick = null;
        // TODO: nick값 받기

        if (act.equals("/signin/facebook")) {
            FBConnection fbConnection = new FBConnection();

//            res.sendRedirect(fbConnection.getFBAuthUrl());

            code = req.getParameter("code");
            System.out.println("fb code : "+code);

            if (code == null || code.equals("")) {
                throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
            }
            accessToken = fbConnection.getAccessToken(code);
//            db.InsertUser(accessToken, nick);

            String graph = fbConnection.getFbGraph(accessToken);
            Map<String, String> fbProfileData = fbConnection.getGrapthData(graph);

            System.out.println("log: FB값 확인(id) : " + fbProfileData.get("id"));
            System.out.println("log: FB값 확인(이름) : " + fbProfileData.get("name"));
        } else if (act.equals("/signin/google")) {
            System.out.println("log: google click");
            GoogleConnection googleConnection = new GoogleConnection();

//            res.sendRedirect(googleConnection.getGoogleAuthUrl());
//            req.getRequestDispatcher(googleConnection.getGoogleAuthUrl()).include(req, res);

//            String fetchedUrl = googleConnection.getFinalRedirectedUrl(googleConnection.getGoogleAuthUrl());
//            System.out.println("FetchedURL is:" + fetchedUrl);

            code = req.getParameter("code");

            System.out.println("log : 구글 주소 쿼리 : "+req.getQueryString());
            System.out.println("log : 구글 주소 : "+req.getRequestURL());
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
}