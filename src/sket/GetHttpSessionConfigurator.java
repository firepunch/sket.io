package sket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Created by KwonJH on 2017-05-11.
 */

// 웹 소켓 접속 시에 HTTP 세션 정보도 같이 전달해준다고 한다.
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config,
                                HandshakeRequest request,
                                HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();

        if (httpSession != null)
            config.getUserProperties().put(HttpSession.class.getName(), httpSession);
    }
}