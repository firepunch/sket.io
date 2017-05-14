package sket.model.action;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import sket.Configure;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

/**
 * Created by firepunch on 2017-05-11.
 */
public class GoogleConnection {
    private static String clientID = "755801497962-25e8cmnp81pcld5r8mfsvmetus9qnnv4.apps.googleusercontent.com";
    private static String clientSecret = Configure.GOOGLE_APP_SECRET;
    private static final String CALLBACK_URL = "http://localhost:8080/signup/google";

    private static HttpTransport httpTransport = null;
    private static JsonFactory jsonFactory = null;
    private static GoogleAuthorizationCodeFlow flow;
    private static Credential credential;

/*
    protected AuthorizationCodeFlow initializeFlow() throws IOException {
        return new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                clientID, clientSecret,
                Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(
                DATA_STORE_FACTORY).setAccessType("offline").build();
    }
*/

    public GoogleConnection() {
    }


    public GoogleConnection(Credential.AccessMethod method,
                            com.google.api.client.http.HttpTransport transport,
                            com.google.api.client.json.JsonFactory jsonFactory,
                            com.google.api.client.http.GenericUrl tokenServerUrl,
                            com.google.api.client.http.HttpExecuteInterceptor clientAuthentication,
                            String clientId,
                            String authorizationServerEncodedUrl) {

    }

    protected GoogleConnection(AuthorizationCodeFlow.Builder builder) {

    }


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

    public String getAccessToken(String authorizationCode) {
        // 2. 리디렉션하여 액세스 토큰 요청
        /*try {
            Credential credential = loadCredent("userid");
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        return authorizationCode;
    }

    public AuthorizationCodeTokenRequest getGoogleGraph(String authorizationCode) {
        // 3. 보호된 자원에 접근하기 위한 권한 정보 저장
        AuthorizationCodeTokenRequest req = null;

        return req;
    }

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