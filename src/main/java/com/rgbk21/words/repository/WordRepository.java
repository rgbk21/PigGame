package com.rgbk21.words.repository;

import com.rgbk21.words.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
  List<Word> findByWordStartingWith(String alphabet);

  List<Word> findWordsByWordEndingWith(String word);
}
