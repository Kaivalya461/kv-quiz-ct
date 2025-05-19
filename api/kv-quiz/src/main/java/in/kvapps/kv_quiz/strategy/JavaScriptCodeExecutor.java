package in.kvapps.kv_quiz.strategy;

import in.kvapps.kv_quiz.model.CodeExecutionResponse;
import lombok.extern.log4j.Log4j2;
import org.graalvm.polyglot.Context;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
public class JavaScriptCodeExecutor implements KvCodeExecutor {
    @Override
    public CodeExecutionResponse execute(String code) {
        // Use a Graalvm execution mechanism
        var response = new CodeExecutionResponse();

        // Redirect System.out to capture console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> future = executor.submit(() -> {
            try (Context context = Context.newBuilder("js")
                    .allowAllAccess(true) // Allows full JavaScript execution
                    .build()) {

                return context.eval("js", code).toString(); // Infinite loop example
            }
        });


        try {
            // Wait for max 2 seconds before stopping execution
            String output = future.get(3, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(true);
            response.setError(e.getMessage());
        } finally {
            executor.shutdown();
        }

        // Restore original System.out
        System.setOut(originalOut);

        // Get captured output
        String capturedOutput = outputStream.toString();

        response.setOutput(capturedOutput);
        return response;
    }
}

