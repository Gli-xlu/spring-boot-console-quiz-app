package ru.otus.hm.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@Setter
@Getter
@ConfigurationProperties(prefix = "application")
public class AppProps {

    private Locale locale;
}
