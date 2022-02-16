package com.rgbk21.wordle;

import java.util.List;

public class WordleRequest {
  List<String> letters;
  List<Boolean> greenPositions;

  public List<String> getLetters() {
    return letters;
  }

  public List<Boolean> getGreenPositions() {
    return greenPositions;
  }
}
