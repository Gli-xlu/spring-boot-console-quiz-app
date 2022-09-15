package ru.otus.hm.service;


import org.springframework.context.MessageSource;
import ru.otus.hm.config.AppProps;

import java.io.InputStream;

public class FileLoader {

    private final AppProps props;
    private final MessageSource messageSource;

    public FileLoader(AppProps props, MessageSource messageSource) {
        this.props = props;
        this.messageSource = messageSource;
    }

    public InputStream loadFileFromResource() {
        var localizedFile = messageSource.getMessage("quiz.file", null, props.getLocale());
        var is = FileLoader.class.getResourceAsStream(localizedFile);
        if (is == null) {
            throw new RuntimeException("File " + localizedFile + "not found in resource");
        }
        return is;
    }
}
