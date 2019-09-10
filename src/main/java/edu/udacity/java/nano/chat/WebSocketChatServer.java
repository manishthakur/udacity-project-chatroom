package edu.udacity.java.nano.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static void sendMessageToAll(Message msg) {

        onlineSessions.keySet().forEach(key -> {
            try {
                Session session = onlineSessions.get(key);
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(new ObjectMapper().writeValueAsString(msg));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session) {
        try {
            onlineSessions.put(session.getId(), session);
            Message message = new Message();
            message.setOnlineCount(onlineSessions.size());
            sendMessageToAll(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        try {
            Message msg = new ObjectMapper().readValue(jsonStr, Message.class);
            msg.setOnlineCount(onlineSessions.size());
            msg.setType("SPEAK");
            sendMessageToAll(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
        try {
            onlineSessions.remove(session.getId());
            Message message = new Message();
            message.setOnlineCount(onlineSessions.size());
            sendMessageToAll(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
