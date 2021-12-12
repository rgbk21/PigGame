package com.rgbk21.model;

import java.util.ArrayList;
import java.util.List;

public class EmailMessage {
  private String title;
  private String html;
  private List<String> recipients;

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

  public EmailMessage addRecipient(String recipient) {
    recipients.add(recipient);
    return this;
  }
}
