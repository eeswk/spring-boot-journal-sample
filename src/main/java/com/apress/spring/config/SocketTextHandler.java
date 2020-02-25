package com.apress.spring.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;

@Component
public class SocketTextHandler  extends TextWebSocketHandler {

    HashMap<String, WebSocketSession> sessions = new HashMap<>();

    //Client에서 메시지가 서버로 전송될때 실행되는
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        try {
            for (String key : sessions.keySet()) {
                WebSocketSession ss = sessions.get(key);
                ss.sendMessage(new TextMessage(payload));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //세션이 생성될때 실행되는
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.put(session.getId(), session);
    }

    //세션이 끝났을때 실행되는
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        super.afterConnectionClosed(session, status);
    }
}
