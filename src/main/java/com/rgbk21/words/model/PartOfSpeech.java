package com.rgbk21.words.model;

public enum PartOfSpeech {
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
