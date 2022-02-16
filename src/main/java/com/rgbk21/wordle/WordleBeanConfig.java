package com.rgbk21.wordle;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.FileNotFoundException;

@Configuration
public class WordleBeanConfig {

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
  public WordsList getWordsList() throws FileNotFoundException {
    return new WordsList();
  }
}
