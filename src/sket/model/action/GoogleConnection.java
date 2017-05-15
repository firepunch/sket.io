package sket.model.action;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import org.json.JSONException;
import org.json.JSONObject;
import sket.Configure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by firepunch on 2017-05-11.
 */
public class GoogleConnection {
    private static String clientID = "755801497962-25e8cmnp81pcld5r8mfsvmetus9qnnv4.apps.googleusercontent.com";
    private static String clientSecret = Configure.GOOGLE_APP_SECRET;
    private static final String REDIRECT_URI = "http://localhost:8080/signin/google/";

    static String accessToken = "";
    private static HttpTransport httpTransport = null;
    private static JsonFactory jsonFactory = null;
    private static GoogleAuthorizationCodeFlow flow;
    private static Credential credential;

    // 구글 로그인 페이지로 redirect해준다.
    public String getGoogleAuthUrl() {
        String googleLoginUrl = "";
        googleLoginUrl = "https://accounts.google.com/o/oauth2/auth?" +
                "client_id="+clientID+
                "&redirect_uri="+REDIRECT_URI+
                "&scope=https://www.googleapis.com/auth/plus.login" +
                "&response_type=code";

        System.out.println("log : google redirect : " + googleLoginUrl);

        return googleLoginUrl;
    }

/*
    protected AuthorizationCodeFlow initializeFlow() throws IOException {
        return new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                clientID, clientSecret,
                Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(
                DATA_STORE_FACTORY).setAccessType("offline").build();
    }
*/

    public AuthorizationCodeTokenRequest newTokenRequest(String authorizationCode) {
        return null;
    }

    public Credential loadCredential(String userId) throws IOException {
        // 1. userID의 권한 정보를 권한 정보소에서 로드
        credential = flow.loadCredential(userId);

        if (credential == null) {
            String url = flow.newAuthorizationUrl().setState("xyz")
                    .setRedirectUri("https://client.example.com/rd").build();
//            response.setRedirect(url);
        }

        return credential;
    }

    private String getGoogleTokenUrl(String code) {
        String googleTokenUrl = "";
        googleTokenUrl = "https://www.googleapis.com/oauth2/v4/token?code="
                + code + "&client_id=" + clientID + "&client_secret=" + clientSecret +
                "&redirect_uri=" + REDIRECT_URI + "&grant_type=authorization_code";

        return googleTokenUrl;
    }

    public static String getFinalURL(String url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setInstanceFollowRedirects(false);
        con.connect();
        con.getInputStream();

        if (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM || con.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
            String redirectUrl = con.getHeaderField("Location");
            return getFinalURL(getFinalURL(redirectUrl));
        }
        return url;
    }

    public static String getFinalRedirectedUrl(String url) {

        HttpURLConnection connection;
        String finalUrl = url;
        try {
            do {
                connection = (HttpURLConnection) new URL(finalUrl)
                        .openConnection();
                connection.setInstanceFollowRedirects(false);
                connection.setUseCaches(false);
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode >= 300 && responseCode < 400) {
                    String redirectedUrl = connection.getHeaderField("Location");
                    if (null == redirectedUrl)
                        break;
                    finalUrl = redirectedUrl;
                    System.out.println("redirected url: " + finalUrl);
                } else
                    break;
            } while (connection.getResponseCode() != HttpURLConnection.HTTP_OK);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalUrl;
    }

    public String getAccessToken(String authorizationCode) {
        // 2. 리디렉션하여 액세스 토큰 요청
        if ("".equals(accessToken)) {
            URL googleURL;
            try {
                googleURL = new URL(authorizationCode);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("Invalid code received " + e);
            }
            URLConnection googleConnection;
            try {
                googleConnection = googleURL.openConnection();
                BufferedReader in;
                in = new BufferedReader(new InputStreamReader(
                        googleConnection.getInputStream()));
                String inputLine;
                inputLine = in.readLine();
                System.out.println("log : google accesstoken : "+inputLine);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to connect with Google " + e);
            }
        }
        return accessToken;
    }

/*
    public AuthorizationCodeTokenRequest getGoogleGraph(String authorizationCode) {
        // 3. 보호된 자원에 접근하기 위한 권한 정보 저장
    }*/

    public Credential getGrapthData(TokenResponse res, String userId) throws IOException {
        // 4. 구글 프로필 정보 저장
        https://www.googleapis.com/auth/userinfo.profile
        credential = flow.createAndStoreCredential(res, userId);

        return credential;
    }

    public String getGoogleGraph(String token) {
        String graph = null;
        try {
            String g = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token="+token;
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
            System.out.println("log : Google graph : "+graph);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in getting Google graph data. " + e);
        }
        return graph;
    }

    public Map getGrapthData(String graph) {
        Map gProfile = new HashMap();
        try {
            JSONObject json = new JSONObject(graph);
            gProfile.put("id", json.getString("id"));
            gProfile.put("name", json.getString("name"));
            gProfile.put("picture", json.getString("picture"));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in parsing FB graph data. " + e);
        }
        return gProfile;
    }


}

/*
            transport = GoogleNetHttpTransport.newTrustedTransport();
            jsonFactory = new JacksonFactory();

            GoogleConnection flow = new GoogleConnection.Builder(
                    transport, jsonFactory,
                    clientID, clientSecret,
                    Arrays.asList(code)
            ).build();

            GoogleTokenResponse response = null;

            response = flow.newTokenRequest(code).setRedirectUri("postmessage").execute();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
*/