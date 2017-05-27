package sket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/*
 * https://stackoverflow.com/questions/21888425/accessing-servletcontext-and-httpsession-in-onmessage-of-a-jsr-356-serverendpo
 * handshake요청을 받은 것을 capture해서 오버라이드함
 * onopen 시 가능한 인자로 값을 바꿈
*/
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        try {
            System.out.println("핸드쉐이응크으 : " + request.getHttpSession());
            config.getUserProperties().put(HttpSession.class.getName(), request.getHttpSession());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}