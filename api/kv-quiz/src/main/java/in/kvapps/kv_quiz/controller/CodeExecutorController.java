package in.kvapps.kv_quiz.controller;

import in.kvapps.kv_quiz.dto.CodeExecutorRequestDto;
import in.kvapps.kv_quiz.service.CodeExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/code-executor")
@CrossOrigin("*")
public class CodeExecutorController {
    @Autowired private CodeExecutorService codeExecutorService;

    @PostMapping("/execute")
    public ResponseEntity<Map<String, String>> execute(@RequestBody CodeExecutorRequestDto requestDto) {
        return codeExecutorService.execute(requestDto);
    }
}