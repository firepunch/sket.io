package sket.model.action;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by ymr on 2017-05-11.
 */
public class GoogleConnection extends AbstractAuthorizationCodeServlet {
    private HttpServletRequest req;

    @Override
    protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
        return new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(),
                new NetHttpTransport(),
                new JacksonFactory(),
                new GenericUrl("https://server.example.com/token"),
                new BasicAuthentication("s6BhdRkqt3", "7Fjfp0ZBr1KtDRbnfVdmIw"),
                "s6BhdRkqt3",
                "https://server.example.com/authorize").setCredentialDataStore(
                StoredCredential.getDefaultDataStore(
                        new FileDataStoreFactory(new File("datastoredir"))))
                .build();
    }

    @Override
    protected String getRedirectUri(HttpServletRequest httpServletRequest) throws ServletException, IOException {
        GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath("/oauth2callback");
        return url.build();
    }

    @Override
    protected String getUserId(HttpServletRequest httpServletRequest) throws ServletException, IOException {
        return req.getSession(true).getId();
    }
}

