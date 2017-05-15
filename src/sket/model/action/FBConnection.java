package sket.model.action;

import org.json.JSONException;
import org.json.JSONObject;
import sket.Configure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class FBConnection {
    private static final String FB_APP_ID = "741189302727195";
    public static final String FB_APP_SECRET = Configure.FB_APP_SECRET;
    private static final String REDIRECT_URI = "http://localhost:8080/";

    static String accessToken = "";

    // 페북 로그인 페이지로 redirect해준다.
    public String getFBAuthUrl() {
        String fbLoginUrl = "";
        try {
            fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
                    + FB_APP_ID + "&redirect_uri="
                    + URLEncoder.encode(REDIRECT_URI, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fbLoginUrl;
    }

    // 페북 토큰을 얻는다.
    private String getFBTokenUrl(String code) {
        String fbGraphUrl = "";

        try {
            fbGraphUrl = "https://graph.facebook.com/oauth/access_token?client_id="
                    + FB_APP_ID + "&client_secret=" + FB_APP_SECRET +
                    "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8") + "&code=" + code;
            System.out.println(fbGraphUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return fbGraphUrl;
    }

    // accessToken을 사용가능하도록 정제한다.
    public String getAccessToken(String code) {
        if ("".equals(accessToken)) {
            URL fbGraphURL;
            try {
                fbGraphURL = new URL(getFBTokenUrl(code));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("Invalid code received " + e);
            }
            URLConnection fbConnection;
            StringBuffer b = null;
            try {
                fbConnection = fbGraphURL.openConnection();
                BufferedReader in;
                in = new BufferedReader(new InputStreamReader(
                        fbConnection.getInputStream()));
                String inputLine;
                b = new StringBuffer();
                while ((inputLine = in.readLine()) != null)
                    b.append(inputLine + "\n");
                in.close();
/*
                inputLine = in.readLine();
                String[] split1 = inputLine.split("\"");
                splited = split1[3].toString();
*/
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to connect with Facebook " + e);
            }
            accessToken = b.toString();
        }
        return accessToken;
    }

    public String getFbGraph(String token) {
        String graph = null;
        try {
            String g = "https://graph.facebook.com/me?fields=id,name&access_token=" + token;
//            String g = "https://graph.facebook.com/me?fields=id,name&access_token=" + Configure.FB_ACCESS_TOKEN;
            URL u = new URL(g);
            URLConnection c = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            String inputLine;
            StringBuffer b = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                b.append(inputLine + "\n");
            }
            in.close();
            graph = b.toString();
            System.out.println("log: FB graph : "+graph);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in getting FB graph data. " + e);
        }
        return graph;
    }

    public Map getGrapthData(String graph) {
        Map fbProfile = new HashMap();
        System.out.println("id");
        System.out.println("name");
        try {
            JSONObject json = new JSONObject(graph);
            fbProfile.put("id", json.getString("id"));
            fbProfile.put("name", json.getString("name"));

            if (json.has("email"))
                fbProfile.put("email", json.getString("email"));

        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in parsing FB graph data. " + e);
        }
        return fbProfile;
    }
}