package in.kvapps.kv_quiz.repository;

import in.kvapps.kv_quiz.model.KvTest;
import in.kvapps.kv_quiz.temp.TempDatabase;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class KvTestRepository {

    public void save(KvTest kvTest) {
        TempDatabase.kvTests.add(kvTest);
    }

    public Optional<KvTest> findBy(String testId) {
        return new ArrayList<>(TempDatabase.kvTests.getCopyOfAllElements())
                .stream()
                .filter(x -> testId.equals(x.getId()))
                .findFirst();
    }

    public List<KvTest> findAll() {
        return new ArrayList<>(TempDatabase.kvTests.getCopyOfAllElements());
    }
}
