package in.kvapps.kv_quiz.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.kvapps.kv_quiz.dto.TestProgressDto;
import in.kvapps.kv_quiz.dto.TestProgressUpdateEventDto;
import in.kvapps.kv_quiz.service.TestProgressService;
import in.kvapps.kv_quiz.temp.LimitedQueue;
import in.kvapps.kv_quiz.temp.SetLimitedDeque;
import in.kvapps.kv_quiz.temp.TempDatabase;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public class MyWebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Set<WebSocketSession> sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Add the New Event Updates to events stored in-memory
        TestProgressUpdateEventDto testProgressUpdateEventDto = parseJsonString(message.getPayload());
        TestProgressService.updateTestProgressDetails(testProgressUpdateEventDto);

        log.debug("Received -> {}", testProgressUpdateEventDto);

        String event = convertToJsonString(TestProgressService.getTestDetails());

        if (event != null) {
            broadcastMessage(event);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.info("New session connected: {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        log.debug("Session disconnected: {}", session.getId());
    }

    private void broadcastMessage(String message) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (Exception e) {
                    log.error("Error sending message to session {}", session.getId(), e);
                }
            }
        }
    }

    private TestProgressUpdateEventDto parseJsonString(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, TestProgressUpdateEventDto.class);
        } catch (Exception e) {
            log.error("MyWebSocketHandler::parseJsonString -> Error while converting StringJson to Object, exMessage: {}", e.getMessage(), e);
            return null;
        }
    }

    private String convertToJsonString(Set<TestProgressDto> queue) {
        try {
            return objectMapper.writeValueAsString(queue);
        } catch (Exception e) {
            log.error("MyWebSocketHandler::convertToJsonString -> Error while writing value as String, exMessage: {}", e.getMessage(), e);
            return null;
        }
    }
}