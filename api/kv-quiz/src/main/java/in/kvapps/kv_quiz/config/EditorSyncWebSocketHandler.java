package in.kvapps.kv_quiz.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.kvapps.kv_quiz.dto.EditorSyncUpdateEventDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public class EditorSyncWebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Set<WebSocketSession> sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Add the New Event Updates to events stored in-memory
        EditorSyncUpdateEventDto editorSyncUpdates = parseJsonString(message.getPayload());

        log.debug("Received -> {}", editorSyncUpdates);

        String event = convertToJsonString(editorSyncUpdates);

        if (event != null) {
            broadcastMessage(event);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.info("EditorSyncWebSocketHandler -> New session connected: {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        log.debug("EditorSyncWebSocketHandler -> Session disconnected: {}", session.getId());
    }

    private void broadcastMessage(String message) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (Exception e) {
                    log.error("EditorSyncWebSocketHandler -> Error sending message to session {}", session.getId(), e);
                }
            }
        }
    }

    private EditorSyncUpdateEventDto parseJsonString(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, EditorSyncUpdateEventDto.class);
        } catch (Exception e) {
            log.error("EditorSyncWebSocketHandler::parseJsonString -> Error while converting StringJson to Object, exMessage: {}", e.getMessage(), e);
            return null;
        }
    }

    private String convertToJsonString(EditorSyncUpdateEventDto updateEvents) {
        try {
            return objectMapper.writeValueAsString(updateEvents);
        } catch (Exception e) {
            log.error("EditorSyncWebSocketHandler::convertToJsonString -> Error while writing value as String, exMessage: {}", e.getMessage(), e);
            return null;
        }
    }
}