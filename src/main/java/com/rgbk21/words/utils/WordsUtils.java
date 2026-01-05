package com.rgbk21.words.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.flogger.FluentLogger;
import com.rgbk21.words.dto.WordEntry;
import com.rgbk21.words.model.PartOfSpeech;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class WordsUtils {
  private static final FluentLogger LOGGER = FluentLogger.forEnclosingClass();

  public static List<WordEntry> fetchWordEntriesFromFile(String relativeFilePath) throws IOException, URISyntaxException {
    LOGGER.atInfo().log("Starting opening file to read and populate words.");
    // Path of file to pass into the method: wordList/Meanings_Cleaned.txt
    URL resourceUrl = WordsUtils.class.getClassLoader().getResource(relativeFilePath);
    if (resourceUrl == null) {
      throw new IOException("Cannot find Resource: " + relativeFilePath);
    }

    return WordsUtils.parseFile(Path.of(resourceUrl.toURI()));
  }

  private static List<WordEntry> parseFile(Path filePath) throws IOException {
    try (Stream<String> lines = Files.lines(filePath)) {
      ImmutableList.Builder<WordEntry> wordEntryBuilder = new ImmutableList.Builder<>();
      Set<String> uniqueWords = new HashSet<>();
      Set<String> duplicateWords = new HashSet<>();
      for (String line : (Iterable<String>) lines::iterator) {
        try {
          WordEntry entry = parseLine(line);
          if (entry != null) {
            wordEntryBuilder.add(entry);
            boolean newWordAdded = uniqueWords.add(entry.word());
            if (!newWordAdded) {
              duplicateWords.add(entry.word());
            }
          }
        } catch (Exception e) {
          throw new RuntimeException("Failed to parse line: \"" + line + "\"", e);
        }
      }
      if (!duplicateWords.isEmpty()) {
        System.out.println("Duplicate words in list: " + duplicateWords);
      }

      return wordEntryBuilder.build();
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

