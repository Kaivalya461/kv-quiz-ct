package in.kvapps.kv_quiz.model;

import in.kvapps.kv_quiz.enums.TestCategory;
import in.kvapps.kv_quiz.enums.TestDifficulty;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
public class KvTest {
    private Long id;
    private String testName; //Ex: Test001-10thMay2025-11:00AM
    private List<Question> questions;
    private int durationInMinutes;
    private TestDifficulty difficultyLevel; //easy, medium, hard
    private TestCategory category; // java, angular
    private ZonedDateTime createdAt;
    private ZonedDateTime scheduledAt;
    private String scheduleBy; // Admin if scheduled for future time, LoggedIn UserName if started manually.
}
