package in.kvapps.kv_quiz.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.kvapps.kv_quiz.model.KvTest;
import in.kvapps.kv_quiz.model.KvTestQuestion;
import in.kvapps.kv_quiz.model.Question;
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
@Profile("prod")
@Log4j2
public class InitialDataLoad {
    @Autowired private QuestionBankService questionBankService;
    @Autowired private ObjectMapper objectMapper;

    @PostConstruct
    public void loadDataForLocalTesting() {
        // Load Test Questions
        try {
            List<Question> questions = parseJson("src/main/resources/test/test-qb2.json");
            questionBankService.updateQuestionBank(questions);
            log.info("Question Bank Initial Load for Prod Executed -> QuestionBank Data Size: {}", questionBankService.getQuestionBank().size());
        } catch (IOException e) {
            log.error("Error while Test Questions at LocalDataLoad::loadDataForLocalTesting, ErrorMessage: {}", e.getMessage(), e);
        }
    }

    public List<Question> parseJson(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), new TypeReference<>() {});
    }
}
