package in.kvapps.kv_quiz.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.kvapps.kv_quiz.model.KvTest;
import in.kvapps.kv_quiz.model.Question;
import in.kvapps.kv_quiz.repository.KvTestRepository;
import in.kvapps.kv_quiz.service.QuestionBankService;
import in.kvapps.kv_quiz.temp.TempDatabase;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Profile("local")
@Log4j2
public class LocalDataLoad {
    @Autowired private QuestionBankService questionBankService;
    @Autowired private ObjectMapper objectMapper;

    @PostConstruct
    public void loadDataForLocalTesting() {
        // Load Test Questions
        try {
            List<Question> questions = parseJson("src/main/resources/test/test-qb2.json");
            questionBankService.updateQuestionBank(questions);
        } catch (IOException e) {
            log.error("Error while Test Questions at LocalDataLoad::loadDataForLocalTesting, ErrorMessage: {}", e.getMessage(), e);
        }

        // Create a Sample MCQTest
        try {
            KvTest mcqTest = parseJsonForMcqTest("src/main/resources/test/sample-mcq-test.json");
            TempDatabase.kvTests.add(mcqTest);
        } catch (IOException e) {
            log.error("Error while Sample MCQTest at LocalDataLoad::loadQuestions, ErrorMessage: {}", e.getMessage(), e);
        }

    }

    public List<Question> parseJson(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), new TypeReference<>() {});
    }

    public KvTest parseJsonForMcqTest(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), new TypeReference<>() {});
    }
}
