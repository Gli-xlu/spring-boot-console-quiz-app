package ru.otus.hm.service;

import ru.otus.hm.dao.Quiz;

import java.io.InputStream;

public interface QuizParser {

    Quiz readQuiz(InputStream inputStream);
}
