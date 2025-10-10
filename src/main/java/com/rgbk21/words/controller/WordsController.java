package com.rgbk21.words.controller;

import com.google.common.base.Preconditions;
import com.rgbk21.words.model.Word;
import com.rgbk21.words.service.WordsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@CrossOrigin(
    origins = {"https://rgbk21.github.io", "http://localhost:63342"},
    allowedHeaders = "*",
    allowCredentials = "true",
    methods = {RequestMethod.GET}
)
@RestController
@RequestMapping("/words")
public class WordsController {
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

  @RequestMapping(value="page", method = RequestMethod.GET)
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

  @RequestMapping(value="alphabet", method = RequestMethod.GET)
  public ResponseEntity<Iterable<Word>> getAllWordsStartingWithAlphabet(@RequestParam Map<String, String> queryParameters) {
    String queryAlphabet = queryParameters.getOrDefault("alphabet", "");
    System.out.println("alphabet: " + queryAlphabet);
    Iterable<Word> allWords = null;
    if (!Objects.equals(queryAlphabet, "")) {
      allWords = wordsService.findWordsThatStartWith(queryAlphabet);
    }
    System.out.println(allWords);
    if (allWords == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(allWords);
    }
  }
}
