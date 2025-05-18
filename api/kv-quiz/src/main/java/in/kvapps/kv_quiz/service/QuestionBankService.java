package in.kvapps.kv_quiz.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.kvapps.kv_quiz.temp.TempDatabase;
import in.kvapps.kv_quiz.config.AppConfig;
import in.kvapps.kv_quiz.model.Question;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class QuestionBankService {
    @Autowired private AppConfig appConfig;
    @Autowired private ObjectMapper objectMapper;

    public List<Question> getQuestionBank() {
        return TempDatabase.questionBank;
    }

    public String uploadQuestionBank(MultipartFile questionBankJson, String accessToken) {
        if (!appConfig.getAccessToken().equals(accessToken)) {
            log.error("Invalid AccessToken received, TokenReceived: {}", accessToken);
            return "Invalid Access Token";
        }

        List<Question> questions = parseJson(questionBankJson);
        updateQuestionBank(questions);

        return "200 Success";
    }

    public void updateQuestionBank(List<Question> newQuestions) {
        TempDatabase.questionBank.addAll(newQuestions);
    }

    private List<Question> parseJson(MultipartFile json) {
        try {
            return objectMapper.readValue(json.getInputStream(), new TypeReference<>() {});
        } catch (Exception exception) {
            log.error("QuestionBankService::uploadQuestionBank -> Error while parsing Questions, ExceptionMessage:{}",
                    exception.getMessage(),
                    exception
            );
            return Collections.emptyList();
        }
    }

    public String clearQuestionBankData(String accessToken) {
        if (!appConfig.getAccessToken().equals(accessToken)) {
            log.error("Invalid AccessToken received, TokenReceived: {}", accessToken);
            return "Invalid Access Token";
        }

        TempDatabase.questionBank.clear();

        return "Successfully cleared the Question Bank Data";
    }

}
