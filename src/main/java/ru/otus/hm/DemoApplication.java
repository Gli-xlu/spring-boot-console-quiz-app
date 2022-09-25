package ru.otus.hm;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.hm.config.AppProps;
import ru.otus.hm.config.QuizProps;

@RequiredArgsConstructor
@SpringBootApplication
@EnableConfigurationProperties({QuizProps.class, AppProps.class})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
