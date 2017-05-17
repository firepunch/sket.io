package sket.model.action;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import sket.Configure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by firepunch on 2017-05-11.
 */
public class GoogleConnection {
    private static String clientID = "755801497962-25e8cmnp81pcld5r8mfsvmetus9qnnv4.apps.googleusercontent.com";
    private static String clientSecret = Configure.GOOGLE_APP_SECRET;
    private static final String REDIRECT_URI = "http://localhost:8080/signin/google/";

    /* 액세스 토큰 받기 -> 사용자 동의 결정 -> 콜백 url로 임시 인증 코드 반환 */
    public List<NameValuePair> getGoogleAuthUrl(String code) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("grant_type", "authorization_code"));
        nameValuePairs.add(new BasicNameValuePair("client_id", clientID));
        nameValuePairs.add(new BasicNameValuePair("client_secret", clientSecret));
        nameValuePairs.add(new BasicNameValuePair("redirect_uri", REDIRECT_URI));
        nameValuePairs.add(new BasicNameValuePair("code", code));

        return nameValuePairs;
    }

    /* 인증 코드를 갱신 토큰 및 액세스 토큰으로 교환 // post 요청*/
    public String getAccessToken(String code) throws IOException {
        String url = "https://accounts.google.com/o/oauth2/token";
        String accesstoken = null;

        HttpPost post = new HttpPost(url);
        HttpClient client = new DefaultHttpClient();

        post.setHeader("Host", "accounts.google.com");
        post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        post.setHeader("Accept-Language", "ko-kr,ko;q=0.8,en-us;q=0.5,en;q=0.3");
        post.setHeader("Connection", "keep-alive");
        post.setHeader("Referer", "https://accounts.google.com/o/oauth2/token");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        post.setEntity(new UrlEncodedFormEntity(getGoogleAuthUrl(code),"UTF-8"));
        HttpResponse response = client.execute(post);
//        int responseCode = response.getStatusLine().getStatusCode();
//        System.out.println("responseCode : " + responseCode);

        try {
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            JSONObject jsonObj = new JSONObject(result.toString());
            accesstoken = jsonObj.getString("access_token");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to connect with Facebook " + e);
        }

        return accesstoken;
    }

    /* 액세스토큰으로 유저의 정보 얻음 */
    public static JSONObject getGoogleGraph(String token) {
        JSONObject graph = null;
        try {
            String g = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=" + token;
            URL u = new URL(g);
            URLConnection c = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuffer result = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine + "\n");
            }
            in.close();

            graph = new JSONObject(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in getting FB graph data. " + e);
        }
        return graph;
    }
}