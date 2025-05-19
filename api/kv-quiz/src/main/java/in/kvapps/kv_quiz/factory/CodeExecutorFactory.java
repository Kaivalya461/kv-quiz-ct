package in.kvapps.kv_quiz.factory;

import in.kvapps.kv_quiz.strategy.JavaCodeExecutor;
import in.kvapps.kv_quiz.strategy.JavaScriptCodeExecutor;
import in.kvapps.kv_quiz.strategy.KvCodeExecutor;
import org.springframework.stereotype.Component;

@Component
public class CodeExecutorFactory {

    public KvCodeExecutor getExecutor(String language) {
        return switch (language.toLowerCase()) {
            case "java","java21" -> new JavaCodeExecutor();
            case "javascript","js" -> new JavaScriptCodeExecutor();
            default -> throw new IllegalArgumentException("Unsupported language: " + language);
        };
    }
}
