package com.rgbk21.words.repository;

import com.rgbk21.words.model.Word;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends PagingAndSortingRepository<Word, Long> {
}
