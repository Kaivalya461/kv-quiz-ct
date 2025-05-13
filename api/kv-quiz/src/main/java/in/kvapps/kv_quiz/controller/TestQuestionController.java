package in.kvapps.kv_quiz.controller;

import in.kvapps.kv_quiz.dto.KvTestDto;
import in.kvapps.kv_quiz.dto.KvTestQuestionDto;
import in.kvapps.kv_quiz.service.TestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-question")
@CrossOrigin("*")
public class TestQuestionController {
    @Autowired private TestQuestionService testQuestionService;

    @GetMapping("/test-id/{testId}/test-category/{testCategory}")
    public ResponseEntity<KvTestQuestionDto> getQuestionsForTest(@PathVariable String testId, @PathVariable String testCategory) {
        return ResponseEntity.ok(testQuestionService.getQuestionsForTest(testId, testCategory));
    }
}
