package in.kvapps.kv_quiz.service;

import in.kvapps.kv_quiz.dto.KvTestQuestionDto;
import in.kvapps.kv_quiz.dto.QuestionDto;
import in.kvapps.kv_quiz.enums.TestCategory;
import in.kvapps.kv_quiz.model.Question;
import in.kvapps.kv_quiz.repository.KvTestQuestionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class TestQuestionService {
    @Autowired
    private KvTestQuestionRepository mcqTestQuestionRepository;

    public KvTestQuestionDto getQuestionsForTest(String testId, String testCategory) {
        TestCategory testCategoryEnum = TestCategory.valueOf(testCategory);

        KvTestQuestionDto testQuestionDto = new KvTestQuestionDto();

        // Questions
        mcqTestQuestionRepository.findBy(testId)
                .ifPresent(data -> {
                    BeanUtils.copyProperties(data, testQuestionDto);
                    var questionList = data.getQuestions()
                            .stream().filter(question -> testCategoryEnum.equals(question.getCategory()))
                            .toList();
                    List<QuestionDto> questionDtoList = new ArrayList<>(); // Dto for UI consumption.
                    // Add Seq Numbers
                    IntStream.range(0, questionList.size())
                            .forEach(idx -> {
                                QuestionDto questionDto = new QuestionDto();
                                Question question = questionList.get(idx);
                                BeanUtils.copyProperties(question, questionDto);
                                questionDto.setSeqNum(idx + 1);
                                questionDtoList.add(questionDto);
                            });
                    testQuestionDto.setQuestions(questionDtoList);
                });


        return testQuestionDto;
    }
}
