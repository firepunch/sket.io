package sket.controllers;

import org.json.JSONObject;
import sket.db.DBConnection;
import sket.model.action.GoogleConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by firepunch on 2017-04-06.
 */

public class GoogleLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String code, nick = "";

    public GoogleLoginController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBConnection db = new DBConnection();
        GoogleConnection googleConnection = new GoogleConnection();

        code = req.getParameter("code");
        if (code == null || code.equals("")) {
            throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
        }
        String token = googleConnection.getAccessToken(code);
        JSONObject gProfileData = GoogleConnection.getGoogleGraph(token);
        String id = gProfileData.getString("id");
        String name = gProfileData.getString("name");
        String picture = gProfileData.getString("picture");

        System.out.println("log : 구글값 확인(id) : " + id);
        System.out.println("log : 구글값 확인(이름) : " + name);
        System.out.println("log : 구글값 확인(사진) : " + picture);
        System.out.println("log : 구글값 확인 : " + req.getQueryString());
        System.out.println("log : 구글값 확인 : " + req.getRequestURI());

/*
        req.setCharacterEncoding("euc-kr");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        System.out.println(req.getQueryString());
        String id = req.getParameter("id");
        String name = req.getParameter("name");
//        String nick = req.getParameter("nick");
        String nick = "imptNick";
        System.out.println(id+name+nick+"GOOGLE");*/


//        db.InsertUser(id, nick, name);
    }

}