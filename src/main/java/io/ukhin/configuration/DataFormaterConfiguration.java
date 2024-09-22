package io.ukhin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Locale;

@Configuration
public class DataFormaterConfiguration {
    @Bean
    public SimpleDateFormat getDataFormatter() {
        return new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    }
}
