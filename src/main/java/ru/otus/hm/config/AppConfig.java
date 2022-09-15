package ru.otus.hm.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.hm.service.CsvQuizParser;
import ru.otus.hm.service.FileLoader;
import ru.otus.hm.service.QuizParser;

@ComponentScan
@Configuration
public class AppConfig {

    @Bean
    FileLoader fileLoader(AppProps props, MessageSource messageSource) {
        return new FileLoader(props, messageSource);
    }

    @Bean
    QuizParser quizParser() {
        return new CsvQuizParser();
    }
}
