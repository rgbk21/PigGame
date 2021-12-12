package com.rgbk21.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;

//@JsonSerialize @JsonDeserialize
public class EmailMessage {
  private String title;
  private String html;
  private final List<Recipient> recipients;

  public EmailMessage() {
    this.recipients = new ArrayList<>();
  }

  public String getTitle() {
    return title;
  }

  public EmailMessage setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getHtml() {
    return html;
  }

  public EmailMessage setHtml(String html) {
    this.html = html;
    return this;
  }

  public EmailMessage addRecipient(Recipient recipient) {
    recipients.add(recipient);
    return this;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("title", title)
        .add("html", html)
        .add("recipients", recipients)
        .toString();
  }
}
