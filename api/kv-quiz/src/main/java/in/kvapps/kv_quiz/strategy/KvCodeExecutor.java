package in.kvapps.kv_quiz.strategy;

import in.kvapps.kv_quiz.model.CodeExecutionResponse;

public interface KvCodeExecutor {
    CodeExecutionResponse execute(String codeToExecute);
}
