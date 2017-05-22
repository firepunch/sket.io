package sket.controllers;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by firepunch on 2017-04-06.
 */

public class GoogleLoginController extends HttpServlet {
    public GoogleLoginController() {
        super();
    }

    public static JSONObject getBody(HttpServletRequest req) throws IOException {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = req.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        JSONObject jsonObj = new JSONObject(stringBuilder.toString());
        return jsonObj;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println(getBody(req));
/*

        StringBuffer jb = new StringBuffer();
        String line = null;
        JSONObject jsonObject;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { throw  e; }

        try {
            jsonObject =  HTTP.toJSONObject(jb.toString());
        } catch (JSONException e) {
            throw new IOException("Error parsing JSON request string");
        }

        System.out.println(jsonObject);
        System.out.println(jsonObject.getString("user"));
*/

        JSONObject json = new JSONObject();

        resp.setCharacterEncoding("euc-kr");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");

        String id = req.getParameter("id");
        String name = req.getParameter("name");
//        String nick = req.getParameter("nick");
        String nick = "imptNick";
        System.out.println(id + name + nick + "  GOOGLE");

        json.put("type", "google");
        json.put("id", "1111");

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();

//        db.InsertUser(id, nick, name);
    }
}