package ru.otus.hm.service;


import ru.otus.hm.config.QuizProps;

import java.io.InputStream;

public class FileLoader {

    private final QuizProps config;

    public FileLoader(QuizProps config) {
        this.config = config;
    }

    public InputStream loadFileFromResource() {
        var is = FileLoader.class.getResourceAsStream(config.getFile());
        if (is == null) {
            throw new RuntimeException("File " + config.getFile() + "not found in resource");
        }
        return is;
    }
}
