package sket.model.action;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import sket.Configure;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by firepunch on 2017-05-11.
 */
public class GoogleConnection {
    private static String clientID = "755801497962-25e8cmnp81pcld5r8mfsvmetus9qnnv4.apps.googleusercontent.com";
    private static String clientSecret = Configure.GOOGLE_APP_SECRET;
    private static final String REDIRECT_URI = "http://localhost:8080/";

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
                +code + "&client_id=" + clientID + "&client_secret=" + clientSecret +
                "&redirect_uri=" + REDIRECT_URI + "&grant_type=authorization_code";
        return googleTokenUrl;
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
        credential = flow.createAndStoreCredential(res, userId);

        return credential;
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