package in.kvapps.kv_quiz.model;

import in.kvapps.kv_quiz.enums.TestDifficulty;
import lombok.Data;

import java.util.List;

@Data
public class    KvTestQuestion {
    private long id;
    private String testId;
    private List<Question> questions;
}
