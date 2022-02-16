package com.rgbk21.wordle;

import java.util.List;

public class WordleResponse {
  List<String> allPossibleAnswers;
  List<String> wordlePossibleAnswers;

  public WordleResponse setAllPossibleAnswers(List<String> allPossibleAnswers) {
    this.allPossibleAnswers = allPossibleAnswers;
    return this;
  }

  public List<String> getAllPossibleAnswers() {
    return allPossibleAnswers;
  }

  public List<String> getWordlePossibleAnswers() {
    return wordlePossibleAnswers;
  }

  public WordleResponse setWordlePossibleAnswers(List<String> wordlePossibleAnswers) {
    this.wordlePossibleAnswers = wordlePossibleAnswers;
    return this;
  }
}
