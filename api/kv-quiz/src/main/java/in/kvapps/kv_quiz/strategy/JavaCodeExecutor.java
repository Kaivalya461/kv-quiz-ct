package in.kvapps.kv_quiz.strategy;

import in.kvapps.kv_quiz.model.CodeExecutionResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Service
@Log4j2
public class JavaCodeExecutor implements KvCodeExecutor {
    @Override
    public CodeExecutionResponse execute(String code) {
        var response = new CodeExecutionResponse();

        try {
            // Save code to temporary file
            Path tempDir = Files.createTempDirectory("CodeExecDir");
            Path filePath = tempDir.resolve("Main.java");
            Files.writeString(filePath, code);


            // Compile Java Code
            Process compileProcess = new ProcessBuilder("javac", filePath.toString()).start();
            compileProcess.waitFor();
            String compileOutput = new String(compileProcess.getErrorStream().readAllBytes());

            if (compileProcess.exitValue() != 0) {
                deleteDirectory(tempDir);
                log.info("CodeExecutorService::execute -> Compilation Failure");
                response.setError("Compilation failed.");
                response.setOutput(compileOutput);
                return response;
            }

            // Execute Java Code
            ProcessBuilder executeProcessBuilder = new ProcessBuilder("java", filePath.toString());
            var tempResponse = executeCode(executeProcessBuilder, response);

            deleteDirectory(tempDir);


            if (tempResponse.getOutput().length() < 3000) {
                response.setOutput(tempResponse.getOutput());
                response.setError(tempResponse.getError());
            } else {
                response.setError("Output length exceeded the character limit");
            }
            return response;

        } catch (Exception e) {
            response.setError(e.getMessage());
            return response;
        }
    }

    /**
     * Recursively deletes a directory and its contents.
     */
    private void deleteDirectory(Path directory) {
        try (Stream<Path> paths = Files.walk(directory)) {
            paths.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            System.err.println("Failed to delete temporary directory: " + e.getMessage());
        }
    }

    private CodeExecutionResponse executeCode(ProcessBuilder executeProcessBuilder, CodeExecutionResponse response) {
        try {
            Process executeProcess = executeProcessBuilder.start();
            // Allow only 3 seconds for execution
            boolean finished = executeProcess.waitFor(3, TimeUnit.SECONDS);

            if (!finished) {
                executeProcess.destroy(); // Forcefully terminate if exceeds time limit
                response.setOutput(new String(executeProcess.getInputStream().readAllBytes()));
                response.setError("Execution timed out.");
            } else {
                response.setOutput(new String(executeProcess.getInputStream().readAllBytes()));
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}

