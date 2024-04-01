package com.rgbk21.words.service;

import com.google.common.base.Preconditions;
import com.rgbk21.words.model.Word;
import com.rgbk21.words.repository.WordRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class WordsService {
  private final WordRepository wordRepository;

  public WordsService(WordRepository wordRepository) {
    this.wordRepository = Preconditions.checkNotNull(wordRepository);
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
}
