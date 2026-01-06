package com.rgbk21.words.controller;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.flogger.FluentLogger;
import com.rgbk21.words.model.Word;
import com.rgbk21.words.service.WordsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(
    origins = {
        "https://rgbk21.github.io",
        "http://localhost:63342"
    },
    allowedHeaders = "*",
    allowCredentials = "true",
    methods = {RequestMethod.GET}
)
@RestController
@RequestMapping("/words")
public class WordsController {
  private static final FluentLogger LOGGER = FluentLogger.forEnclosingClass();

  private final WordsService wordsService;

  public WordsController(WordsService wordsService) {
    this.wordsService = Preconditions.checkNotNull(wordsService);
  }

  @GetMapping("/all")
  public ResponseEntity<Iterable<Word>> getAllWords() {
    Iterable<Word> allWords = wordsService.findAll();
    if (allWords == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(allWords);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Word> findById(@PathVariable Long id) {
    Word word = wordsService.findById(id);
    if (word == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(word);
    }
  }

  @RequestMapping(value = "page", method = RequestMethod.GET)
  public ResponseEntity<Iterable<Word>> getAllWordsByPage(@RequestParam Map<String, String> queryParameters) {
    String offsetStr = queryParameters.getOrDefault("offset", "");
    String pageSizeStr = queryParameters.getOrDefault("pageSize", "");
    System.out.println("offset: " + offsetStr);
    System.out.println("pageSizeStr: " + pageSizeStr);
    Iterable<Word> allWords = null;
    if (!Objects.equals(offsetStr, "") && !Objects.equals(pageSizeStr, "")) {
      int offset = Integer.parseInt(offsetStr);
      int pageSize = Integer.parseInt(pageSizeStr);
      if (offset >= 0 && pageSize > 0) {
        allWords = wordsService.findWordsWithPagination(Integer.parseInt(offsetStr), Integer.parseInt(pageSizeStr));
      }
    }
    if (allWords == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(allWords);
    }
  }

  @RequestMapping(value = "alphabet", method = RequestMethod.GET)
  public ResponseEntity<Iterable<Word>> getAllWordsStartingWithAlphabet(@RequestParam Map<String, String> queryParameters) {
    String queryAlphabet = queryParameters.getOrDefault("alphabet", "");
    System.out.println("alphabet: " + queryAlphabet);
    Iterable<Word> allWords = null;
    if (!Objects.equals(queryAlphabet, "")) {
      allWords = wordsService.findWordsThatStartWith(queryAlphabet);
    }
//    System.out.println(allWords);
    if (allWords == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(allWords);
    }
  }

  @RequestMapping(value = "wordsEndingIn", method = RequestMethod.GET)
  public ResponseEntity<Iterable<Word>> getAllWordsEndingIn(@RequestParam Map<String, String> queryParameters) {
    String words = queryParameters.getOrDefault("words", "");
    List<String> wordsList = Arrays.stream(words.split(",")).toList();
    System.out.println("words: " + wordsList);
    Iterable<Word> allWords = null;
    Set<Word> matchingWords = new HashSet<>();
    if (!Objects.equals(words, "")) {
      for (String word : wordsList) {
        List<Word> words1 = Lists.newArrayList(wordsService.findWordsThatEndWith(word));
        matchingWords.addAll(words1);
      }
    }

    allWords = Lists.newArrayList(matchingWords);

    System.out.println(allWords);
    if (allWords == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(allWords);
    }
  }

  // @PostMapping("/populateDB")
  public ResponseEntity<Map<String, String>> populateDatabase() {
    LOGGER.atInfo().log("Received request for populating DB");

    try {
      long count = wordsService.populateDatabaseFromFile();
      return ResponseEntity.ok(ImmutableMap.of("message", "Successfully populated database", "wordsAdded", Long.toString(count)));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ImmutableMap.of("message", "Failed to populate database", "error", e.getMessage()));
    }
  }
}
