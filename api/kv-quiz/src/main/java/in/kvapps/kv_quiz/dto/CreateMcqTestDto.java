package in.kvapps.kv_quiz.dto;

import in.kvapps.kv_quiz.enums.TestCategory;
import in.kvapps.kv_quiz.enums.TestDifficulty;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class CreateMcqTestDto {
    private String loggedInUser;
    private TestCategory testCategory;
    private TestDifficulty testDifficulty;
    private int questionsSize;
    private int durationInMinutes;
    private ZonedDateTime scheduledAt;
    private String scheduledBy;
}
