package in.kvapps.kv_quiz.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class TestProgressDto {
    private String testId;
    private Set<UserProgressDto> users = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(testId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TestProgressDto user)) return false;
        return Objects.equals(testId, user.testId);
    }
}
