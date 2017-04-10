package sket.controllers;

import javax.servlet.http.HttpServlet;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by hojak on 2017-04-06.
 */

@ServerEndpoint("/test")
public class WebSocket extends HttpServlet{
    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println("onOpen()");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("OnMessage("+message+")");
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose()");
    }

    @OnError
    public void onError(Throwable throwable, Session session) {
        System.out.println("onError()");
        throwable.printStackTrace();
    }
}
