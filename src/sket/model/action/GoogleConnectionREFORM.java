/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sket.model.action;


import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.PeopleFeed;
import com.google.gson.Gson;
import sket.Configure;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

/**
 * Simple server to demonstrate how to use Google+ Sign-In and make a request
 * via your own server.
 *
 * @author joannasmith@google.com (Joanna Smith)
 * @author vicfryzel@google.com (Vic Fryzel)
 */
public class GoogleConnectionREFORM {
    /*
 * Default HTTP transport to use to make HTTP requests.
 */
    private static final HttpTransport TRANSPORT = new NetHttpTransport();

    /*
     * Default JSON factory to use to deserialize JSON.
     */
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    /*
 * Gson object to serialize JSON responses to requests to this servlet.
 */
    private static final Gson GSON = new Gson();

    private static final String CLIENT_ID = "755801497962-25e8cmnp81pcld5r8mfsvmetus9qnnv4.apps.googleusercontent.com";
    public static final String CLIENT_SECRET = Configure.GOOGLE_APP_SECRET;
    private static final String APPLICATION_NAME = "sket.io";
    private static final String REDIRECT_URI = "http://localhost:8080/LoginController/";
    private static final String auth_uri = "https://accounts.google.com/o/oauth2/auth";
    private static final String token_uri = "https://accounts.google.com/o/oauth2/token";

    /**
     * Initialize a session for the current user, and render index.html.
     */
    public static class MainServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            // This check serves the signin button image
            if ("/signin_button.png".equals(request.getServletPath())) {
                File staticFile = new File("./static/signin_button.png");
                FileInputStream fileStream = new FileInputStream(staticFile);
                byte []buf = new byte[(int)staticFile.length()];
                fileStream.read(buf);
                response.setContentType("image/png");
                response.getOutputStream().write(buf);
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }

            // This check prevents the "/" handler from handling all requests by default
            if (!"/".equals(request.getServletPath())) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            response.setContentType("text/html");
            try {
                // Create a state token to prevent request forgery.
                // Store it in the session for later validation.
                String state = new BigInteger(130, new SecureRandom()).toString(32);
                request.getSession().setAttribute("state", state);
                // Fancy way to read index.html into memory, and set the client ID
                // and state values in the HTML before serving it.
                response.getWriter().print(new Scanner(new File("index.html"), "UTF-8")
                        .useDelimiter("\\A").next()
                        .replaceAll("[{]{2}\\s*CLIENT_ID\\s*[}]{2}", CLIENT_ID)
                        .replaceAll("[{]{2}\\s*STATE\\s*[}]{2}", state)
                        .replaceAll("[{]{2}\\s*APPLICATION_NAME\\s*[}]{2}",
                                APPLICATION_NAME)
                        .toString());
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (FileNotFoundException e) {
                // When running the quickstart, there was some path issue in finding
                // index.html.  Double check the quickstart guide.
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().print(e.toString());
            }
        }
    }

    /**
     * Upgrade given auth code to token, and store it in the session.
     * POST body of request should be the authorization code.
     * Example URI: /connect?state=...&gplus_id=...
     */
    public static class ConnectServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            response.setContentType("application/json");

            // Only connect a user that is not already connected.
            String tokenData = (String) request.getSession().getAttribute("token");
            if (tokenData != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().print(GSON.toJson("Current user is already connected."));
                return;
            }
            // Ensure that this is no request forgery going on, and that the user
            // sending us this connect request is the user that was supposed to.
            if (!request.getParameter("state").equals(request.getSession().getAttribute("state"))) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().print(GSON.toJson("Invalid state parameter."));
                return;
            }
            // Normally the state would be a one-time use token, however in our
            // simple case, we want a user to be able to connect and disconnect
            // without reloading the page.  Thus, for demonstration, we don't
            // implement this best practice.
            //request.getSession().removeAttribute("state");

            ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
            getContent(request.getInputStream(), resultStream);
            String code = new String(resultStream.toByteArray(), "UTF-8");

            try {
                // Upgrade the authorization code into an access and refresh token.
                GoogleTokenResponse tokenResponse =
                        new GoogleAuthorizationCodeTokenRequest(TRANSPORT, JSON_FACTORY,
                                CLIENT_ID, CLIENT_SECRET, code, "postmessage").execute();

                // You can read the Google user ID in the ID token.
                // This sample does not use the user ID.
                GoogleIdToken idToken = tokenResponse.parseIdToken();
                String gplusId = idToken.getPayload().getSubject();

                // Store the token in the session for later use.
                request.getSession().setAttribute("token", tokenResponse.toString());
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().print(GSON.toJson("Successfully connected user."));
            } catch (TokenResponseException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().print(GSON.toJson("Failed to upgrade the authorization code."));
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().print(GSON.toJson("Failed to read token data from Google. " +
                        e.getMessage()));
            }
        }

        /*
         * Read the content of an InputStream.
         *
         * @param inputStream the InputStream to be read.
         * @return the content of the InputStream as a ByteArrayOutputStream.
         * @throws IOException
         */
        static void getContent(InputStream inputStream, ByteArrayOutputStream outputStream)
                throws IOException {
            // Read the response into a buffered stream
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            int readChar;
            while ((readChar = reader.read()) != -1) {
                outputStream.write(readChar);
            }
            reader.close();
        }
    }

    /**
     * Revoke current user's token and reset their session.
     */
    public static class DisconnectServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            response.setContentType("application/json");

            // Only disconnect a connected user.
            String tokenData = (String) request.getSession().getAttribute("token");
            if (tokenData == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().print(GSON.toJson("Current user not connected."));
                return;
            }
            try {
                // Build credential from stored token data.
                GoogleCredential credential = new GoogleCredential.Builder()
                        .setJsonFactory(JSON_FACTORY)
                        .setTransport(TRANSPORT)
                        .setClientSecrets(CLIENT_ID, CLIENT_SECRET).build()
                        .setFromTokenResponse(JSON_FACTORY.fromString(
                                tokenData, GoogleTokenResponse.class));
                // Execute HTTP GET request to revoke current token.
                HttpResponse revokeResponse = TRANSPORT.createRequestFactory()
                        .buildGetRequest(new GenericUrl(
                                String.format(
                                        "https://accounts.google.com/o/oauth2/revoke?token=%s",
                                        credential.getAccessToken()))).execute();
                // Reset the user's session.
                request.getSession().removeAttribute("token");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().print(GSON.toJson("Successfully disconnected."));
            } catch (IOException e) {
                // For whatever reason, the given token was invalid.
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(GSON.toJson("Failed to revoke token for given user."));
            }
        }
    }

    /**
     * Get list of people user has shared with this app.
     */
    public static class PeopleServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            response.setContentType("application/json");

            // Only fetch a list of people for connected users.
            String tokenData = (String) request.getSession().getAttribute("token");
            if (tokenData == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().print(GSON.toJson("Current user not connected."));
                return;
            }
            try {
                // Build credential from stored token data.
                GoogleCredential credential = new GoogleCredential.Builder()
                        .setJsonFactory(JSON_FACTORY)
                        .setTransport(TRANSPORT)
                        .setClientSecrets(CLIENT_ID, CLIENT_SECRET).build()
                        .setFromTokenResponse(JSON_FACTORY.fromString(
                                tokenData, GoogleTokenResponse.class));
                // Create a new authorized API client.
                Plus service = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential)
                        .setApplicationName(APPLICATION_NAME)
                        .build();
                // Get a list of people that this user has shared with this app.
                PeopleFeed people = service.people().list("me", "visible").execute();
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().print(GSON.toJson(people));
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().print(GSON.toJson("Failed to read data from Google. " +
                        e.getMessage()));
            }
        }
    }
}