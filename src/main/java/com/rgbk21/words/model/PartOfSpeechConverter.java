package com.rgbk21.words.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

@Converter(autoApply = false)
public class PartOfSpeechConverter implements AttributeConverter<EnumSet<PartOfSpeech>, String> {
  private static final String DELIMITER = ",";

  @Override
  public String convertToDatabaseColumn(EnumSet<PartOfSpeech> attribute) {
    if (attribute == null || attribute.isEmpty()) {
      return null;
    }

    // Convert EnumSet to comma-separated string: "NOUN,VERB,ADJECTIVE"
    return attribute.stream()
        .map(Enum::name)
        .collect(Collectors.joining(DELIMITER));
  }

  @Override
  public EnumSet<PartOfSpeech> convertToEntityAttribute(String dbData) {
    if (dbData == null || dbData.trim().isEmpty()) {
      return EnumSet.noneOf(PartOfSpeech.class);
    }

    // Convert comma-separated string back to EnumSet
    return Arrays.stream(dbData.split(DELIMITER))
        .map(String::trim)
        .map(PartOfSpeech::valueOf)
        .collect(Collectors.toCollection(() -> EnumSet.noneOf(PartOfSpeech.class)));
  }
}
