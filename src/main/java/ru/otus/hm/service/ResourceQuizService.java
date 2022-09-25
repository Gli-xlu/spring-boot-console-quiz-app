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
    public String printQuiz() {
        if (quiz == null) {
            return getMessage("quiz.empty");
        }
        var builder = new StringBuilder();
        quiz.questions().forEach(q -> {
            builder.append(q.getQuestion());
            builder.append("\n");
            if (q.hasAnswers()) {
                builder.append(q.getAnswers());
                builder.append("\n");
            }
            builder.append("\n");
        });
        return builder.toString();
    }

    @Override
    public String testStudent(Student student) {
        var in = new Scanner(System.in);

        var score = test(in);
        return getMessage("quiz.score", new String[] {student.surname(), student.name(), String.valueOf(score)});
    }

    private String getMessage(String code) {
        return getMessage(code, null);
    }

    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, props.getLocale());
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
