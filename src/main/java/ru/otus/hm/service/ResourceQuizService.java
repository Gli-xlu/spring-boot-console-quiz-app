package ru.otus.hm.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.hm.config.AppProps;
import ru.otus.hm.dao.Question;
import ru.otus.hm.dao.Quiz;

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
    public void printQuiz() {
        if (quiz == null) {
            printMessage("quiz.empty");
            return;
        }
        quiz.questions().forEach(q -> {
            System.out.println(q.getQuestion());
            if (q.hasAnswers()) {
                System.out.println(q.getAnswers());
            }
            System.out.println();
        });
    }

    @Override
    public void testStudent() {
        var in = new Scanner(System.in);

        printMessage("quiz.name");
        var firstName = in.nextLine();
        printMessage("quiz.surname");
        var lastName = in.nextLine();

        var score = test(in);
        System.out.println();
        printMessage("quiz.score", new String[] {lastName, firstName, String.valueOf(score)});
    }

    private void printMessage(String code) {
        printMessage(code, null);
    }

    private void printMessage(String code, Object[] args) {
        var localizedQuestion = messageSource.getMessage(code, args, props.getLocale());
        System.out.println(localizedQuestion);
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
