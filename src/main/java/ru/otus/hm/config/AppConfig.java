package ru.otus.hm.config;

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
    FileLoader fileLoader(QuizProps config) {
        return new FileLoader(config);
    }

    @Bean
    QuizParser quizParser() {
        return new CsvQuizParser();
    }
}
