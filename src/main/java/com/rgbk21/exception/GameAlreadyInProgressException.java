package com.rgbk21.exception;

public class GameAlreadyInProgressException extends Exception {

  private String message;

  public GameAlreadyInProgressException(String message) {
    this.message = message;
  }
}
