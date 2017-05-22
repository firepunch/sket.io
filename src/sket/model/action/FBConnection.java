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
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FBConnection {
    private static final String FB_APP_ID = "741189302727195";
    public static final String FB_APP_SECRET = Configure.FB_APP_SECRET;
    private static final String REDIRECT_URI = "http://localhost:8080/signin/facebook/";

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
            String splited = null;
            try {
                fbConnection = fbGraphURL.openConnection();
                BufferedReader in;
                in = new BufferedReader(new InputStreamReader(
                        fbConnection.getInputStream()));
                String inputLine;
                inputLine = in.readLine();
                String[] split1 = inputLine.split("\"");
                splited = split1[3].toString();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to connect with Facebook " + e);
            }
            accessToken = splited;
        }
        return accessToken;
    }

    public String getFbGraph(String token) {
        String graph = null;
        try {
            String g = "https://graph.facebook.com/me?fields=id,name&access_token=" + token;
            URL u = new URL(g);
            URLConnection c = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuffer b = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                b.append(inputLine + "\n");
            }
            in.close();
            graph = b.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in getting FB graph data. " + e);
        }
        return graph;
    }

    public Map getGrapthData(String graph) {
        Map fbProfile = new HashMap();
        try {
            JSONObject json = new JSONObject(graph);
            fbProfile.put("id", json.getString("id"));
            fbProfile.put("name", json.getString("name"));
//            fbProfile.put("picture", json.getString("picture"));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in parsing FB graph data. " + e);
        }
        return fbProfile;
    }
}
