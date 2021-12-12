package com.rgbk21.exception;

public class NoExistingGamesException extends Exception {

  private final String message;

  public NoExistingGamesException(String message) {
    this.message = message;
  }
}
