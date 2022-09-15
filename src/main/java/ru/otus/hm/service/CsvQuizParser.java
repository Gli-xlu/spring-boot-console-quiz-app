package ru.otus.hm.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import ru.otus.hm.dao.Question;
import ru.otus.hm.dao.QuestionCsv;
import ru.otus.hm.dao.Quiz;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CsvQuizParser implements QuizParser {

    private static final String QUESTION_HEADER = "Question";
    private static final String CORRECT_ANSWER_HEADER = "Answer";

    private static final String[] ANSWER_HEADERS = new String[] {"Answer 1", "Answer 2", "Answer 3", "Answer 4"};

    @Override
    public Quiz readQuiz(InputStream inputStream) {
        try {
            var reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            var records = CSVFormat.EXCEL.builder()
                    .setHeader(getHeaders())
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(reader);
            var questions = new ArrayList<Question>();
            for (CSVRecord csvRecord : records) {
                var answers = readAnswers(csvRecord);
                var question = new QuestionCsv(csvRecord.get(QUESTION_HEADER), answers, csvRecord.get(CORRECT_ANSWER_HEADER));
                questions.add(question);
            }
            return new Quiz(questions);
        } catch (IOException e) {
            throw new RuntimeException("Can't read csv");
        }
    }

    private String[] getHeaders() {
        var answerLength = ANSWER_HEADERS.length;
        var headers = new String[answerLength + 2];
        headers[0] = QUESTION_HEADER;
        System.arraycopy(ANSWER_HEADERS, 0, headers, 1, answerLength);
        headers[headers.length - 1] = CORRECT_ANSWER_HEADER;
        return headers;
    }

    private List<String> readAnswers(CSVRecord csvRecord) {
        var firstAnswer = csvRecord.get(ANSWER_HEADERS[0]);
        if (!firstAnswer.isEmpty()) {
            var answers = new ArrayList<String>();
            for (String answerHeader: ANSWER_HEADERS) {
                answers.add(csvRecord.get(answerHeader));
            }
            return answers;
        }
        return Collections.emptyList();
    }
}
