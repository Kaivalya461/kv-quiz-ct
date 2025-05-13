package in.kvapps.kv_quiz.util;

import in.kvapps.kv_quiz.enums.TestCategory;
import in.kvapps.kv_quiz.enums.TestDifficulty;
import in.kvapps.kv_quiz.model.Question;
import lombok.experimental.UtilityClass;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@UtilityClass
public class CommonUtilService {
    public long generateRandomLong() {
        return (long) (Math.random() * 10000) + 1;
    }

    public static String generateTestName(long id) {
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd'th'MMMuuuu-hh:mma");
        String formattedDate = now.format(formatter);

        return "Test" + id + "-" + formattedDate;
    }

    public static String generateTestId(long id) {
        Random random = new Random();
        char firstLetter = (char) ('A' + random.nextInt(26));
        char secondLetter = (char) ('A' + random.nextInt(26));
        char thirdLetter = (char) ('A' + random.nextInt(26));
        return "" + firstLetter + secondLetter + thirdLetter + "-" + id;
    }

    public static List<Question> getQuestionsForTest(List<Question> questionBank,
                                                     int quesRequestSize,
                                                     TestCategory testCategory,
                                                     TestDifficulty testDifficulty) {
        return questionBank.stream()
                .filter(question -> testCategory.equals(question.getCategory()))
                .filter(question -> Objects.isNull(testDifficulty) || testDifficulty.equals(question.getDifficulty())) // If Difficulty is specified use it for filter, else pick from all ques.
                .limit(quesRequestSize)
                .collect(Collectors.collectingAndThen(Collectors.toList(), filteredList -> {
                    Collections.shuffle(filteredList);
                    return filteredList;
                }));
    }
}
