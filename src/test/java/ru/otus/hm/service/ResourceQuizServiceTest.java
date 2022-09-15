package ru.otus.hm.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.otus.hm.config.AppProps;
import ru.otus.hm.dao.QuestionCsv;
import ru.otus.hm.dao.Quiz;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ResourceQuizServiceTest {

    private static final Quiz QUIZ = new Quiz(List.of(new QuestionCsv("Q1", Collections.emptyList(), "1"),
            new QuestionCsv("Q2", List.of("a1", "a2", "a3", "a4"), "1")));
    private static final String MESSAGE = "Test message";

    @Mock
    private QuizParser quizParser;

    @Mock
    private FileLoader fileLoader;

    @Mock
    private MessageSource messageSource;

    @Mock
    private AppProps props;

    @InjectMocks
    private ResourceQuizService resourceQuizService;

    @BeforeEach
    void setUp() {
        Mockito.lenient().when(fileLoader.loadFileFromResource())
                .thenReturn(InputStream.nullInputStream());
        Mockito.lenient().when(quizParser.readQuiz(any()))
                .thenReturn(QUIZ);
        Mockito.lenient().when(props.getLocale())
                .thenReturn(Locale.ENGLISH);
        Mockito.lenient().when(messageSource.getMessage(any(), any(), any()))
                .thenReturn(MESSAGE);
    }

    @Test
    void printQuiz_nullQuiz() {
        Mockito.lenient().when(quizParser.readQuiz(any()))
                .thenReturn(null);
        assertDoesNotThrow(() -> resourceQuizService.printQuiz());
    }

    @Test
    void printQuiz_emptyList() {
        Mockito.lenient().when(quizParser.readQuiz(any()))
                .thenReturn(new Quiz(Collections.emptyList()));
        assertDoesNotThrow(() -> resourceQuizService.printQuiz());
    }

    @Test
    void printQuiz_success() {
        assertDoesNotThrow(() -> resourceQuizService.printQuiz());
    }
}
