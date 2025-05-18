package in.kvapps.kv_quiz.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class UserProgressDto {
    private String name;
    private int progress;
    private boolean testCompleted;
    private int answeredQuestions;
    private int correctAnswers;
    private int totalQuestions;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UserProgressDto)) return false;
        UserProgressDto user = (UserProgressDto) obj;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
