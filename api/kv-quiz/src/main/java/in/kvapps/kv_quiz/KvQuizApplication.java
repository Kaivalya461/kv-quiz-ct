package in.kvapps.kv_quiz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
public class KvQuizApplication {

	public static void main(String[] args) {
		SpringApplication.run(KvQuizApplication.class, args);
	}

	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		// Register the JavaTimeModule
		JavaTimeModule module = new JavaTimeModule();

		// Define the default ZonedDateTime format
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a z");

		// Use the custom serializer with the defined format
//		module.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(formatter));

		// Register the module in the ObjectMapper
		objectMapper.registerModule(module);
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objectMapper;
	}
}
