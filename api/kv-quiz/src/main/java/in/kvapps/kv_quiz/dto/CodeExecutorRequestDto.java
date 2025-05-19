package in.kvapps.kv_quiz.dto;

import lombok.Data;

@Data
public class CodeExecutorRequestDto {
    private String lang;
    private String content;
    private String fileName;
}
