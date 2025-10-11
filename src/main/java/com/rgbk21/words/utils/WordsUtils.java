package com.rgbk21.words.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordsUtils {
  public static void main(String[] args) throws IOException, URISyntaxException {
    // The path inside getResources is relative to the "resources" folder
    final String resourcePath = "wordList/Meanings_Cleaned.txt";

    URL resourceUrl = WordsUtils.class.getClassLoader().getResource(resourcePath);
    if (resourceUrl == null) {
      throw new IOException("Cannot find Resource: " + resourcePath);
    }

    // Convert the resource KURL to a Path to be used by the parseFile method.
    List<WordEntry> wordEntries = WordsUtils.parseFile(Path.of(resourceUrl.toURI()));
    System.out.println(wordEntries);
  }

  public static List<WordEntry> parseFile(Path filePath) throws IOException {
    try (Stream<String> lines = Files.lines(filePath)) {
      List<WordEntry> wordEntries = new ArrayList<>();
      for (String line : (Iterable<String>) lines::iterator) {
        try {
          WordEntry entry = parseLine(line);
          if (entry != null) {
            wordEntries.add(entry);
          }
        } catch (Exception e) {
          throw new RuntimeException("Failed to parse line: \"" + line + "\"", e);
        }
      }
      return wordEntries;
    }
  }

  private static WordEntry parseLine(String line) {
    String trimmedLine = line.trim();
    if (trimmedLine.isEmpty() || trimmedLine.startsWith("---") || trimmedLine.matches("\\d+")) {
      return null; // Skip empty lines, separators, and lines containing only numbers.
    }

    int lastSpaceIndex = trimmedLine.lastIndexOf(' ');
    int firstSpaceIndex = trimmedLine.indexOf(' ');

    // Ensure the line has at least two space-separated parts
    if (lastSpaceIndex <= firstSpaceIndex) {
      return null; // Malformed line
    }

    String word = trimmedLine.substring(0, firstSpaceIndex);
    String partsOfSpeechStr = trimmedLine.substring(lastSpaceIndex + 1);
    String meaning = trimmedLine.substring(firstSpaceIndex + 1, lastSpaceIndex).trim();

    EnumSet<PartOfSpeech> partsOfSpeech = Arrays.stream(partsOfSpeechStr.split("/"))
        .map(part -> part.replace(".", "").trim())
        .map(PartOfSpeech::fromString)
        .collect(Collectors.toCollection(() -> EnumSet.noneOf(PartOfSpeech.class)));

    if (word.isEmpty() || meaning.isEmpty() || partsOfSpeech.isEmpty()) {
      return null; // Invalid entry
    }

    return new WordEntry(word, meaning, EnumSet.copyOf(partsOfSpeech));
  }
}

record WordEntry(String word, String meaning, EnumSet<PartOfSpeech> partOfSpeech) {}

enum PartOfSpeech {
  UNDEFINED, NOUN, VERB, ADVERB, ADJECTIVE, PREPOSITION;

  public static PartOfSpeech fromString(String text) {
    if (text == null || text.trim().isEmpty()) {
      return UNDEFINED;
    }

    // Handle common aliases from the text file
    return switch (text) {
      case "ADJ" -> ADJECTIVE;
      case "ADV" -> ADVERB;
      case "N" -> NOUN;
      default -> PartOfSpeech.valueOf(text.toUpperCase());
    };
  }
}