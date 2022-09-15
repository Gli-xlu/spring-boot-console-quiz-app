package ru.otus.hm.service;

import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvQuizParserTest {

    @Test
    void readQuiz_nullInputStream() {
        var parser = new CsvQuizParser();
        var is = InputStream.nullInputStream();
        var quiz = parser.readQuiz(is);
        assertEquals(0, quiz.questions().size());
    }
}
