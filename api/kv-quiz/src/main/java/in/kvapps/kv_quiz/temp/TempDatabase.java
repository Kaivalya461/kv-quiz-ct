package in.kvapps.kv_quiz.temp;

import in.kvapps.kv_quiz.dto.TestProgressDto;
import in.kvapps.kv_quiz.model.KvTest;
import in.kvapps.kv_quiz.model.KvTestQuestion;
import in.kvapps.kv_quiz.model.Question;

import java.util.ArrayList;
import java.util.List;

// Class which holds QuestionBank and other Data
public class TempDatabase {
    public static List<Question> questionBank = new ArrayList<>();
    public static LimitedQueue<KvTest> kvTests = new LimitedQueue<>(40);
    public static LimitedQueue<KvTestQuestion> kvTestQuestions = new LimitedQueue<>(100);

    // Real time test updates of all users.
    public static SetLimitedDeque<TestProgressDto> testProgressDetails = new SetLimitedDeque<>(10);
}
