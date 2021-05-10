package com.rgbk21.spring;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;

@Configuration
public class GameWebConfig implements WebMvcConfigurer {

    public GameWebConfig() {
        super();
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        Optional<HttpMessageConverter<?>> converter = converters.stream()
                .filter(c -> c instanceof AbstractJackson2HttpMessageConverter)
                .findFirst();

        if (converter.isPresent()) {
            AbstractJackson2HttpMessageConverter jacksonConverter = (AbstractJackson2HttpMessageConverter) converter.get();
            jacksonConverter.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            jacksonConverter.getObjectMapper().enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
    }
}
