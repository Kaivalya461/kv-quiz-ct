package in.kvapps.kv_quiz.service;

import in.kvapps.kv_quiz.dto.CreateMcqTestDto;
import in.kvapps.kv_quiz.dto.KvTestDto;
import in.kvapps.kv_quiz.dto.KvTestQuestionDto;
import in.kvapps.kv_quiz.dto.QuestionDto;
import in.kvapps.kv_quiz.enums.TestCategory;
import in.kvapps.kv_quiz.model.KvTest;
import in.kvapps.kv_quiz.model.KvTestQuestion;
import in.kvapps.kv_quiz.model.Question;
import in.kvapps.kv_quiz.repository.KvTestQuestionRepository;
import in.kvapps.kv_quiz.repository.KvTestRepository;
import in.kvapps.kv_quiz.util.CommonUtilService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Log4j2
public class McqTestService {
    @Autowired private QuestionBankService questionBankService;
    @Autowired private KvTestRepository mcqTestRepository;
    @Autowired private KvTestQuestionRepository testQuestionRepository;

    public KvTest createMcqTest(CreateMcqTestDto request) {
        long id = CommonUtilService.generateRandomLong();
        String testId = CommonUtilService.generateTestId(id);

        List<Question> questionBank = questionBankService.getQuestionBank();

        KvTest kvTest = KvTest.builder()
                .id(testId)
                .testName(CommonUtilService.generateTestName(id))
                .durationInMinutes(request.getDurationInMinutes())
                .difficultyLevel(request.getTestDifficulty())
                .category(request.getTestCategory())
                .createdAt(ZonedDateTime.now())
                .scheduledAt(request.getScheduledAt())
                .scheduleBy(request.getLoggedInUser())
                .build();

        //Questions - Prepare Questions for all categories
        List<Question> allQuestions = new ArrayList<>(); // all questions including all categories... JAVA, ANGULAR,...
        Arrays.stream(TestCategory.values())
                .forEach(value -> {
                    var questions = CommonUtilService.getQuestionsForTest(questionBank, request.getQuestionsSize(), value, request.getTestDifficulty());
                    allQuestions.addAll(questions);
                });

        KvTestQuestion kvTestQuestion = new KvTestQuestion();
        kvTestQuestion.setId(CommonUtilService.generateRandomLong());
        kvTestQuestion.setTestId(testId);
        kvTestQuestion.setQuestions(allQuestions);

        testQuestionRepository.save(kvTestQuestion);

        log.info("kvTest Created, kvTestObj: {}", kvTest);

        mcqTestRepository.save(kvTest);

        return kvTest;
    }

    public KvTestDto getTestDetails(String testId) {
        KvTest testDetail = mcqTestRepository.findBy(testId).orElse(null);
        if (null == testDetail) {
            log.warn("McqTestService::getTestDetails -> Test Details NOT FOUND for testId: {}", testId);
            return KvTestDto.builder().build();
        }

        KvTestDto response = KvTestDto.builder().build();
        BeanUtils.copyProperties(testDetail, response);

        return response;
    }
}
