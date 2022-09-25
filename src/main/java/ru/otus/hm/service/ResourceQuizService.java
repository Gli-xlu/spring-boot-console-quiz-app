package ru.otus.hm.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.hm.config.AppProps;
import ru.otus.hm.dao.Question;
import ru.otus.hm.dao.Quiz;
import ru.otus.hm.dao.Student;

import java.util.Objects;
import java.util.Scanner;

@Service
public class ResourceQuizService implements QuizService {
    private final Quiz quiz;
    private final MessageSource messageSource;
    private final AppProps props;

    public ResourceQuizService(FileLoader fileLoader, QuizParser quizParser, MessageSource messageSource, AppProps props) {
        quiz = quizParser.readQuiz(fileLoader.loadFileFromResource());
        this.messageSource = messageSource;
        this.props = props;
    }

    @Override
    public String testStudent(Student student) {
        var in = new Scanner(System.in);

        var score = test(in);
        return getTestResultMessage(new String[] {student.surname(), student.name(), String.valueOf(score)});
    }

    private String getTestResultMessage(Object[] args) {
        return messageSource.getMessage("quiz.score", args, props.getLocale());
    }

    private int test(Scanner in) {
        var score = 0;

        for (Question q: quiz.questions()) {
            System.out.println(q.getQuestion());
            if (q.hasAnswers()) {
                System.out.println(q.getAnswers());
            }
            var answer = in.nextLine();
            if (Objects.equals(answer.toLowerCase(), q.getCorrectAnswer())) {
                score++;
            }
        }
        return score;
    }
}
