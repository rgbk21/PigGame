package com.rgbk21.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailResponse {
  private String id;
  private String message;

  public String getId() {
    return id;
  }

  public EmailResponse setId(String id) {
    this.id = id;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public EmailResponse setMessage(String message) {
    this.message = message;
    return this;
  }
}
