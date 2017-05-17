package sket.controllers;

import org.json.JSONObject;
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
    private String code = "";

    public GoogleLoginController() {
        super();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GoogleConnection googleConnection = new GoogleConnection();

        code = req.getParameter("code");
        if (code == null || code.equals("")) {
            throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
        }
        String token = googleConnection.getAccessToken(code);
        JSONObject info = GoogleConnection.getGoogleGraph(token);
        System.out.println("log : 구글값 확인(id) : " + info.getString("id"));
//        String name = info.getString("name");
//        name=new String(name.getBytes("ISO-8859-1"),"UTF-8");
        System.out.println("log : 구글값 확인(이름) : "+ info.getString("name"));
        System.out.println("log : 구글값 확인(사진) : "+info.getString("picture"));
        //TODO: 클라에 값 전달

        resp.sendRedirect("http://localhost:8080");
        return;
    }
}