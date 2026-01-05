package com.rgbk21.words.service;

import com.google.common.collect.ImmutableList;
import com.google.common.flogger.FluentLogger;
import com.rgbk21.words.model.Word;
import com.rgbk21.words.repository.WordRepository;
import com.rgbk21.words.dto.WordEntry;
import com.rgbk21.words.utils.WordsUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;

@Service
public class WordsService {
  private static final FluentLogger LOGGER = FluentLogger.forEnclosingClass();

  private final WordRepository wordRepository;

  public WordsService(WordRepository wordRepository) {
    this.wordRepository = wordRepository;
  }

  public Iterable<Word> findAll() {
    return wordRepository.findAll();
  }

  public Word findById(Long id) {
    return wordRepository.findById(id).orElse(null);
  }

  public Iterable<Word> findWordsWithPagination(int offset, int pageSize) {
    return wordRepository.findAll(PageRequest.of(offset, pageSize));
  }

  public Iterable<Word> findWordsThatStartWith(String alphabet) {
    return wordRepository.findByWordStartingWith(alphabet);
  }

  @Transactional
  public long populateDatabaseFromFile() throws IOException, URISyntaxException {
    // Clear existing words. More efficient that `deleteAll`. Apparently.
    wordRepository.deleteAllInBatch();

    // Fetch word entries from file.
    List<WordEntry> wordEntriesFromFile = WordsUtils.fetchWordEntriesFromFile("wordList/Meanings_Cleaned.txt");
    LOGGER.atInfo().log("Fetched %d words from file.", wordEntriesFromFile.size());

    // Convert WordEntry to Word entities.
    ImmutableList<Word> words = wordEntriesFromFile.stream()
        .map(entry -> new Word(entry.word(), entry.meaning(), entry.partOfSpeech()))
        .collect(toImmutableList());

    // Save all words to database
    List<Word> savedWords = wordRepository.saveAll(words);
    LOGGER.atInfo().log("Populated DB with %d words.", savedWords.size());

    return savedWords.size();
  }
}
