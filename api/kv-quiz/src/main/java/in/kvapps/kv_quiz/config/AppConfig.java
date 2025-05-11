package in.kvapps.kv_quiz.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppConfig {

    @Value("${kv.access-token}")
    private String accessToken;
}
