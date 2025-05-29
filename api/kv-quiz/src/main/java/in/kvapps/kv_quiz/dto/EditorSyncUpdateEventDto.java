package in.kvapps.kv_quiz.dto;

import lombok.Data;

import java.util.Set;

@Data
public class EditorSyncUpdateEventDto {
    private String type; // cursorUpdate, selectionUpdate
    private String sessionId;
    private String userId;
    private String userColor;

    // for cursorUpdate events
    private int lineNo;
    private int column;

    // for selectionUpdate events
    private int start;
    private int end;

    // for editorText Updates events, like insertText, replaceText, deleteText
    private int index;
    private String text;
    private int length;

    // session details
    private Set<String> activeUsers;
}
