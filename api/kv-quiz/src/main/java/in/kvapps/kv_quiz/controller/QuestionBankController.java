package in.kvapps.kv_quiz.controller;

import in.kvapps.kv_quiz.model.Question;
import in.kvapps.kv_quiz.service.QuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/question-bank")
@CrossOrigin("*")
public class QuestionBankController {
    @Autowired private QuestionBankService questionBankService;

    @GetMapping
    public ResponseEntity<List<Question>> getQuestionBank() {
        return ResponseEntity.ok(questionBankService.getQuestionBank());
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadQuestionBank(@RequestBody MultipartFile jsonFile, @RequestParam String accessToken) {
        return ResponseEntity.ok(questionBankService.uploadQuestionBank(jsonFile, accessToken));
    }
}
