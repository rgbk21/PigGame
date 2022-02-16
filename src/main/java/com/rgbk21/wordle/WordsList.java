package com.rgbk21.wordle;

import org.springframework.context.annotation.Scope;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordsList {

  private final List<String> allFiveLetterWords;
  private final List<String> wordleFiveLetterWords;
  private final Set<String> wordleWordsSet;

  public WordsList() throws FileNotFoundException {
    allFiveLetterWords = readWordsFromResourcesFile("wordList/AllFiveLetterWords.txt");
    wordleFiveLetterWords = readWordsFromResourcesFile("wordList/PossibleAnswers.txt");
    wordleWordsSet = new HashSet<>(wordleFiveLetterWords);
  }

  public List<String> getAllFiveLetterWords() {
    return allFiveLetterWords;
  }

  public List<String> getWordleFiveLetterWords() {
    return wordleFiveLetterWords;
  }

  public Set<String> getWordleWordsSet() {
    return wordleWordsSet;
  }

  private List<String> readWordsFromResourcesFile(String fileName) throws FileNotFoundException {
    List<String> readWords = new ArrayList<>();

    InputStream allPossibleWordsStream = WordsList.class.getClassLoader().getResourceAsStream(fileName);
    if (allPossibleWordsStream == null) throw new FileNotFoundException();

    try (
        InputStreamReader inputStreamReader = new InputStreamReader(allPossibleWordsStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
    ) {
      String word;
      while ((word = br.readLine()) != null) {
        readWords.add(word);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return readWords;
  }
}
