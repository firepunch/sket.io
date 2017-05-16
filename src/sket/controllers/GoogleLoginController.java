package sket.controllers;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
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

public class GoogleLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String code = "";
    private AuthorizationCodeFlow flow;

    public GoogleLoginController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
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