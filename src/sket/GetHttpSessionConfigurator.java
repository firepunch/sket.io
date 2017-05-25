package sket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/*
 * https://stackoverflow.com/questions/21888425/accessing-servletcontext-and-httpsession-in-onmessage-of-a-jsr-356-serverendpo
 * handshake요청을 받은 것을 capture해서 오버라이드함
 * onopen시 가능한 인자로 값을 바꿈
*/
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        try {
             HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}