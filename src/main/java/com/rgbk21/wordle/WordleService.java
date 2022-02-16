package com.rgbk21.wordle;

import com.google.common.base.Preconditions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WordleService {
  private static final Log LOGGER = LogFactory.getLog(WordleService.class);

  private final WordsList wordsList;

  @Autowired
  WordleService(WordsList wordsList) {
    this.wordsList = Preconditions.checkNotNull(wordsList);
  }

  public WordleResponse getPossibleAnswers(WordleRequest wordleRequest) {
    List<String> allFiveLetterWords = wordsList.getAllFiveLetterWords();
    Set<String> validWordleWords = wordsList.getWordleWordsSet();

    String greenPosition = createRegexPatternForGreenPositions(wordleRequest);
    String orangePosition = createRegexPatternForOrangePositions(wordleRequest);
    Pattern greenPattern = Pattern.compile(greenPosition, Pattern.CASE_INSENSITIVE);
    Pattern orangePattern = Pattern.compile(orangePosition, Pattern.CASE_INSENSITIVE);

    List<String> allPossibleAnswers = new ArrayList<>();
    for (int i = 0; i < allFiveLetterWords.size(); i++) {
      String currWord = allFiveLetterWords.get(i);
      Matcher greenMatcher = greenPattern.matcher(currWord);
      Matcher orangeMatcher = orangePattern.matcher(currWord);
      if (greenMatcher.matches() && orangeMatcher.matches()) {
        allPossibleAnswers.add(currWord);
       }
    }

    List<String> wordlePossibleAnswers = new ArrayList<>();
    for (String answer : allPossibleAnswers) {
      if (validWordleWords.contains(answer)) {
        wordlePossibleAnswers.add(answer);
      }
    }

    WordleResponse response = new WordleResponse();
    response
        .setAllPossibleAnswers(allPossibleAnswers)
        .setWordlePossibleAnswers(wordlePossibleAnswers);

    return response;
  }

  private String createRegexPatternForOrangePositions(WordleRequest wordleRequest) {
    StringBuilder basePattern = new StringBuilder("^");
    for (int i = 0; i < wordleRequest.getLetters().size(); i++) {
      if (!wordleRequest.getLetters().get(i).isEmpty() && !wordleRequest.getGreenPositions().get(i)) {
        basePattern
            .append("(?=.*")
            .append(wordleRequest.getLetters().get(i))
            .append(")");
      }
    }
    basePattern.append(".+");

    return basePattern.toString();
  }

  private String createRegexPatternForGreenPositions(WordleRequest wordleRequest) {
    StringBuilder basePattern = new StringBuilder("^");
    String anyCharRegex = "[a-z]";

    for (int i = 0; i < wordleRequest.getGreenPositions().size(); i++) {
      if (wordleRequest.getGreenPositions().get(i)) {
        basePattern
            .append("[")
            .append(wordleRequest.getLetters().get(i))
            .append("]");
      } else {
        basePattern.append(anyCharRegex);
      }
    }

    basePattern.append("$");

    return basePattern.toString();
  }

}
