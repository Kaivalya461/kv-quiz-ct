package in.kvapps.kv_quiz.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class TestProgressUpdateEventDto {
    private String testId;
    private UserProgressDto userProgress;

    @Override
    public int hashCode() {
        return Objects.hash(testId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TestProgressUpdateEventDto user)) return false;
        return Objects.equals(testId, user.testId);
    }
}
