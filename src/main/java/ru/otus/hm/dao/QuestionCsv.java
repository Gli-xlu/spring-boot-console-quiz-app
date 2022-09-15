package ru.otus.hm.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class QuestionCsv implements Question {

    private final String question;
    private final List<String> answers;
    private final String correctAnswers;

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public String getCorrectAnswer() {
        return correctAnswers;
    }

    @Override
    public boolean hasAnswers() {
        return !answers.isEmpty();
    }

    @Override
    public String getAnswers() {
        return "\t" + StringUtils.arrayToDelimitedString(answers.toArray(), "\n\t");
    }
}
