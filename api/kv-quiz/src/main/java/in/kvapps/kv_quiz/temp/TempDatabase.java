package in.kvapps.kv_quiz.temp;

import in.kvapps.kv_quiz.model.KvTest;
import in.kvapps.kv_quiz.model.Question;

import java.util.ArrayList;
import java.util.List;

// Class which holds QuestionBank and other Data
public class TempDatabase {
    public static List<Question> questionBank = new ArrayList<>();
    public static LimitedQueue<KvTest> kvTests = new LimitedQueue<>(10);
}
