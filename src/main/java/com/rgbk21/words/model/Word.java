package com.rgbk21.words.model;

import jakarta.persistence.*;

import java.util.EnumSet;

@Entity()
@Table(name = "words_table")
public class Word {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long wordId;

  @Column(nullable = false, unique = true)
  private String word;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String meaning;

  @Convert(converter = PartOfSpeechConverter.class)
  @Column(nullable = false)
  private EnumSet<PartOfSpeech> partOfSpeech;

  // For JPA use only.
  public Word() {
  }

  public Word(String word, String meaning, EnumSet<PartOfSpeech> partOfSpeech) {
    this.word = word;
    this.meaning = meaning;
    this.partOfSpeech = partOfSpeech;
  }

  public Long getWordId() {
    return wordId;
  }

  public String getWord() {
    return word;
  }

  public String getMeaning() {
    return meaning;
  }

  public EnumSet<PartOfSpeech> getPartOfSpeech() {
    // Return a defensive copy to prevent external modification
    return partOfSpeech != null ? EnumSet.copyOf(partOfSpeech) : EnumSet.noneOf(PartOfSpeech.class);
  }
}
