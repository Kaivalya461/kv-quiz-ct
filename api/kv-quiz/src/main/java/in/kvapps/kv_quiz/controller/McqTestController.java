package in.kvapps.kv_quiz.controller;

import in.kvapps.kv_quiz.dto.CreateMcqTestDto;
import in.kvapps.kv_quiz.dto.KvTestDto;
import in.kvapps.kv_quiz.model.KvTest;
import in.kvapps.kv_quiz.service.McqTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mcq-test")
@CrossOrigin("*")
public class McqTestController {
    @Autowired private McqTestService mcqTestService;

    @PostMapping
    public ResponseEntity<KvTest> createMcqTest(@RequestBody CreateMcqTestDto requestBody) {
        return ResponseEntity.ok(mcqTestService.createMcqTest(requestBody));
    }

    @GetMapping("/test-id/{testId}")
    public ResponseEntity<KvTestDto> getTestDetails(@PathVariable String testId) {
        return ResponseEntity.ok(mcqTestService.getTestDetails(testId));
    }

    @GetMapping()
    public ResponseEntity<List<KvTestDto>> getAllTestDetails() {
        return ResponseEntity.ok(mcqTestService.getAllTestDetails());
    }
}
