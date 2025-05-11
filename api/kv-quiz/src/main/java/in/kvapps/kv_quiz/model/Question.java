package in.kvapps.kv_quiz.model;

import in.kvapps.kv_quiz.enums.TestCategory;
import in.kvapps.kv_quiz.enums.TestDifficulty;
import lombok.Data;

import java.util.List;

@Data
public class Question {
    private Long id;
    private String questionText;
    private List<String> options;
    private String correctAnswer;
    private TestCategory category; // java, angular
    private TestDifficulty difficulty; //easy, medium, hard
}
