package in.kvapps.kv_quiz.service;

import in.kvapps.kv_quiz.dto.CodeExecutorRequestDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Service
@Log4j2
public class CodeExecutorService {

    public ResponseEntity<Map<String, String>> execute(CodeExecutorRequestDto requestDto) {
        log.info("CodeExecutorService::execute triggered");

        Map<String, String> response = new HashMap<>();

        if (requestDto.getContent() != null && requestDto.getContent().length() > 5000) {
            response.put("error", "Code Length exceeded the limit of 5000 characters");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            // Save code to temporary file
            Path tempDir = Files.createTempDirectory("CodeExecDir");
            Path filePath = tempDir.resolve("Main.java");
            Files.writeString(filePath, requestDto.getContent());


            // Compile Java Code
            Process compileProcess = new ProcessBuilder("javac", filePath.toString()).start();
            compileProcess.waitFor();
            String compileOutput = new String(compileProcess.getErrorStream().readAllBytes());

            if (compileProcess.exitValue() != 0) {
                response.put("error", "Compilation failed.");
                response.put("output", compileOutput);
                deleteDirectory(tempDir);
                log.info("CodeExecutorService::execute -> Compilation Failure");
                return ResponseEntity.ok().body(response);
            }

            // Execute Java Code
            ProcessBuilder executeProcessBuilder = new ProcessBuilder("java", filePath.toString());
            String output = executeCode(executeProcessBuilder, response);

            deleteDirectory(tempDir);

            response.put("output", output);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
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

    private String executeCode(ProcessBuilder executeProcessBuilder, Map<String, String> response) {
        String output = null;
        try {
            Process executeProcess = executeProcessBuilder.start();
            // Allow only 3 seconds for execution
            boolean finished = executeProcess.waitFor(3, TimeUnit.SECONDS);

            if (!finished) {
                executeProcess.destroy(); // Forcefully terminate if exceeds time limit
                output = new String(executeProcess.getInputStream().readAllBytes());
                response.put("error", "Execution timed out.");
            } else {
                output = new String(executeProcess.getInputStream().readAllBytes());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return output;
    }
}
