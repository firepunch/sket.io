package sket.controllers;

import org.json.JSONObject;
import sket.db.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        DBConnection db = new DBConnection();

        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String token = req.getParameter("access_token");
//        String nick = req.getParameter("nick");
        String nick = "imptNick";
        System.out.println(id + name + nick + "  FB");

        json.put("type", "facebook");
        json.put("id", token);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
//        out.print(json);
//        out.flush();

//        db.InsertUser(id, nick, name);
    }



/*
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
*/
}