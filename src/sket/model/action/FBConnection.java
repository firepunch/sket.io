package sket.model.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class FBConnection {
    public static final String FB_APP_ID = "741189302727195";
    public static final String FB_APP_SECRET = "66b7c9302459527e06c2501cc69fb78b";
    public static final String REDIRECT_URI = "http://localhost:8080/LoginController/";

    static String accessToken = "";

    public String getFBAuthUrl() {
        String fbLoginUrl = "";
        try {
            fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
                    + FBConnection.FB_APP_ID + "&redirect_uri="
                    + URLEncoder.encode(FBConnection.REDIRECT_URI, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fbLoginUrl;
    }

    public String getFBGraphUrl(String code) {
        String fbGraphUrl = "";
        fbGraphUrl = "https://graph.facebook.com/oauth/access_token?client_id="
                + FBConnection.FB_APP_ID + "&client_secret=" +
                FB_APP_SECRET + "&grant_type=client_credentials";

        return fbGraphUrl;

    }

    public String getAccessToken(String code) {
        if ("".equals(accessToken)) {
            URL fbGraphURL;
            try {
                fbGraphURL = new URL(getFBGraphUrl(code));
                System.out.println(fbGraphURL);
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
                System.out.print(inputLine);
                String[] split1 = inputLine.split("\"");
                splited = split1[3].toString();
                in.close();
                System.out.println(splited);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to connect with Facebook "
                        + e);
            }
            accessToken = splited;
/*            if (accessToken.startsWith("{")) {
                throw new RuntimeException("ERROR: Access Token Invalid: "
                        + accessToken);
            }*/
        }
        return accessToken;
    }
}