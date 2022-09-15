package ru.otus.hm.dao;


public interface Question {

    String getQuestion();

    String getCorrectAnswer();

    boolean hasAnswers();

    String getAnswers();
}
