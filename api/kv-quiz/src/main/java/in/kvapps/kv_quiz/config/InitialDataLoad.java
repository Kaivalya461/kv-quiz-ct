package in.kvapps.kv_quiz.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.kvapps.kv_quiz.model.KvTest;
import in.kvapps.kv_quiz.model.KvTestQuestion;
import in.kvapps.kv_quiz.model.Question;
import in.kvapps.kv_quiz.service.QuestionBankService;
import in.kvapps.kv_quiz.temp.TempDatabase;
import in.kvapps.kv_quiz.util.EncryptDecryptUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

@Component
@Profile("prod")
@Log4j2
public class InitialDataLoad {
    @Autowired private QuestionBankService questionBankService;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private AppConfig appConfig;

    private static final String BASE_STORAGE_BUCKET_URL_ENC = "UfIvPzG32TdrB4aJoOC5b4jOSJWwqPkG6u8kTRVlEUbBP/zqRsNYbf0Pv4f1nbTYwirD95E2GKW8jVOt+38Hfmy6QVq+Soy0y4rY8mOaTaI=";

    @PostConstruct
    public void loadDataForLocalTesting() {
        // Load Test Questions
        try {
            String decryptedUrl = EncryptDecryptUtil.decrypt(BASE_STORAGE_BUCKET_URL_ENC, appConfig.getAccessToken()+appConfig.getAccessToken()) + "/prod-qb-01.json";
            List<Question> questions = loadDataFromCloudBucket(decryptedUrl);
            questionBankService.updateQuestionBank(questions);
            log.info("Question Bank Initial Load for Prod Executed -> QuestionBank Data Size: {}", questionBankService.getQuestionBank().size());
        } catch (Exception e) {
            log.error("Error while Test Questions at LocalDataLoad::loadDataForLocalTesting, ErrorMessage: {}", e.getMessage(), e);
        }

    }

    private List<Question> loadDataFromCloudBucket(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder jsonContent = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line).append("\n");
                }
                reader.close();
                return objectMapper.readValue(jsonContent.toString(), new TypeReference<>() {});
            } else {
                throw new RuntimeException("Failed to fetch the JSON file. HTTP response code: " + responseCode + ", fileURL: " + fileUrl);
            }
        } catch (Exception e) {
            log.error("Exception at InitialDataLoad::loadDataFromCloudBucket, ErrorMessage: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
