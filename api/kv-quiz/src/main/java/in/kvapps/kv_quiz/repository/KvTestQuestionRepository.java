package in.kvapps.kv_quiz.repository;

import in.kvapps.kv_quiz.model.KvTestQuestion;
import in.kvapps.kv_quiz.temp.TempDatabase;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class KvTestQuestionRepository {

    public void save(KvTestQuestion kvTestQuestion) {
        TempDatabase.kvTestQuestions.add(kvTestQuestion);
    }

    public Optional<KvTestQuestion> findBy(String testId) {
        return new ArrayList<>(TempDatabase.kvTestQuestions.getCopyOfAllElements())
                .stream()
                .filter(x -> testId.equals(x.getTestId()))
                .findFirst();
    }
}
