package com.rgbk21.exception;

public class InvalidGameException extends Exception {

  private final String message;

  public InvalidGameException(String message) {
    super(message);
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
