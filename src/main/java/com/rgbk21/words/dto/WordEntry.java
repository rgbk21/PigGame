package com.rgbk21.words.dto;

import com.rgbk21.words.model.PartOfSpeech;

import java.util.EnumSet;

/**
 * An immutable data carrier that represents a single word entry parsed from the source text file.
 * This record serves as a Data Transfer Object (DTO) to hold the structured data
 * before it is processed or persisted.
 *
 * @param word The word itself.
 * @param meaning The definition associated with the word.
 * @param partOfSpeech An {@link EnumSet} containing the grammatical parts of speech for the word (e.g., NOUN, VERB).
 */
public record WordEntry(String word, String meaning, EnumSet<PartOfSpeech> partOfSpeech) {}
