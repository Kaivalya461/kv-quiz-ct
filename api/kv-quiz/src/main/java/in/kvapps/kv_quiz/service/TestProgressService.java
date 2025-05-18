package in.kvapps.kv_quiz.service;

import in.kvapps.kv_quiz.dto.TestProgressDto;
import in.kvapps.kv_quiz.dto.TestProgressUpdateEventDto;
import in.kvapps.kv_quiz.temp.TempDatabase;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class TestProgressService {
    public void updateTestProgressDetails(TestProgressUpdateEventDto event) {
        // First time event?
        Map<String, TestProgressDto> testProgressDtoMap = TempDatabase.testProgressDetails.getCopyOfAllElements()
                .stream()
                .collect(
                        Collectors.toMap(TestProgressDto::getTestId, Function.identity(), (x1, x2) -> x1)
                );
        if (!testProgressDtoMap.containsKey(event.getTestId())) {
            TestProgressDto newTestProgress = new TestProgressDto();
            newTestProgress.setTestId(event.getTestId());
            newTestProgress.getUsers().add(event.getUserProgress());
            TempDatabase.testProgressDetails.add(newTestProgress);
            return;
        }

        // Update UserProgress Events
        var objToUpdate = testProgressDtoMap.get(event.getTestId());
        var userSet = objToUpdate.getUsers();

        userSet.remove(event.getUserProgress());
        userSet.add(event.getUserProgress());
    }

    public Set<TestProgressDto> getTestDetails() {
        return TempDatabase.testProgressDetails.getCopyOfAllElements();
    }
}
