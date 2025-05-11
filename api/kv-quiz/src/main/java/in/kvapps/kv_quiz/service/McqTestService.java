package in.kvapps.kv_quiz.service;

import in.kvapps.kv_quiz.dto.CreateMcqTestDto;
import in.kvapps.kv_quiz.model.KvTest;
import in.kvapps.kv_quiz.model.Question;
import in.kvapps.kv_quiz.util.CommonUtilService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Log4j2
public class McqTestService {
    @Autowired private QuestionBankService questionBankService;

    public String createMcqTest(CreateMcqTestDto request) {
        long id = CommonUtilService.generateRandomLong();

        List<Question> questionBank = questionBankService.getQuestionBank();

        KvTest kvTest = KvTest.builder()
                .id(id)
                .testName(CommonUtilService.generateTestString(id))
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

        return "Test Created Successfully";
    }
}
