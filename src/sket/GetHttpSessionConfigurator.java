package sket;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        try {
            HttpSession session = (HttpSession) request.getHttpSession();
            config.getUserProperties().put(HttpSession.class.getName(), session);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}