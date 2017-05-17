package sket.controllers;

import sket.db.DBConnection;
import sket.model.action.FBConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by firepunch on 2017-04-06.
 */

public class FBLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String code, accessToken, nick = "";

    public FBLoginController() {
        super();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);

        DBConnection db = new DBConnection();
        FBConnection fbConnection = new FBConnection();

        // TODO: nick값 받기
        code = req.getParameter("code");
        if (code == null || code.equals("")) {
            throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
        }
        accessToken = fbConnection.getAccessToken(code);
        String graph = fbConnection.getFbGraph(accessToken);
        Map<String, String> fbProfileData = fbConnection.getGrapthData(graph);
        String id = fbProfileData.get("id");
        String name = fbProfileData.get("name");
//        String picture = fbProfileData.get("picture");

        //TODO: 클라에 값 전달
        System.out.println("log: FB값 확인(id) : " + id);
        System.out.println("log: FB값 확인(이름) : " + name);
//        System.out.println("log: FB값 확인(사진) : " + picture);

        db.InsertUser(id, nick, name);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("http://localhost:8080");
        return;
    }
}