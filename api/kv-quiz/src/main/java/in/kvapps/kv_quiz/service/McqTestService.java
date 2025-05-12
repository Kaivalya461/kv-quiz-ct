package in.kvapps.kv_quiz.service;

import in.kvapps.kv_quiz.dto.CreateMcqTestDto;
import in.kvapps.kv_quiz.dto.KvTestDto;
import in.kvapps.kv_quiz.model.KvTest;
import in.kvapps.kv_quiz.model.Question;
import in.kvapps.kv_quiz.repository.KvTestRepository;
import in.kvapps.kv_quiz.util.CommonUtilService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Log4j2
public class McqTestService {
    @Autowired private QuestionBankService questionBankService;
    @Autowired private KvTestRepository mcqTestRepository;

    public String createMcqTest(CreateMcqTestDto request) {
        long id = CommonUtilService.generateRandomLong();

        List<Question> questionBank = questionBankService.getQuestionBank();

        KvTest kvTest = KvTest.builder()
                .id(CommonUtilService.generateTestId(id))
                .testName(CommonUtilService.generateTestName(id))
                .questions(CommonUtilService.getQuestionsForTest(
                        questionBank, request.getQuestionsSize(), request.getTestCategory(), request.getTestDifficulty())
                )
                .durationInMinutes(request.getDurationInMinutes())
                .difficultyLevel(request.getTestDifficulty())
                .category(request.getTestCategory())
                .createdAt(ZonedDateTime.now())
                .scheduledAt(request.getScheduledAt())
                .scheduleBy(request.getLoggedInUser())
                .build();


        log.info("kvTest Created with QuestionsSize:{}, kvTestObj: {}", kvTest.getQuestions().size(), kvTest);

        mcqTestRepository.save(kvTest);

        return "Test Created Successfully";
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
