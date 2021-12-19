package com.rgbk21.utils;

public class Message {

  private String messageText;

  public Message(String messageText) {
    this.messageText = messageText;
  }

  public String getMessageText() {
    return messageText;
  }

  public Message setMessageText(String messageText) {
    this.messageText = messageText;
    return this;
  }
}
