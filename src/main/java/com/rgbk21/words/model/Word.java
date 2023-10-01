package com.rgbk21.words.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "words_table")
public class Word {
  @Id
  private Long word_id;

  private String word;

  private String meaning;

  // For JPA use only.
  public Word() {}

  public Long getWord_id() {
    return word_id;
  }

  public String getWord() {
    return word;
  }

  public String getMeaning() {
    return meaning;
  }
}
