package in.kvapps.kv_quiz.service;

import in.kvapps.kv_quiz.dto.CodeExecutorRequestDto;
import in.kvapps.kv_quiz.factory.CodeExecutorFactory;
import in.kvapps.kv_quiz.model.CodeExecutionResponse;
import in.kvapps.kv_quiz.strategy.KvCodeExecutor;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Service
@Log4j2
@AllArgsConstructor
public class CodeExecutorService {

    private final CodeExecutorFactory executorFactory;


    public CodeExecutionResponse executeCode(String code, String language) {
        KvCodeExecutor executor = executorFactory.getExecutor(language);
        return executor.execute(code);
    }

    public ResponseEntity<Map<String, String>> execute(CodeExecutorRequestDto requestDto) {
        log.info("CodeExecutorService::execute triggered");

        Map<String, String> response = new HashMap<>();

        if (requestDto.getContent() != null && requestDto.getContent().length() > 5000) {
            response.put("error", "Code Length exceeded the limit of 5000 characters");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            var executionResponse = executeCode(requestDto.getContent(), requestDto.getLang());
            response.put("output", executionResponse.getOutput());
            response.put("error", executionResponse.getError());
        } catch (Exception exception) {
            response.put("error", exception.getMessage());
            log.error("CodeExecutorService::execute failed -> ErrorMsg: {}", exception.getMessage());
        }

        return ResponseEntity.ok(response);
    }
}
