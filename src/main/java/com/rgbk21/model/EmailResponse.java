package com.rgbk21.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailResponse {
  private String _id;
  private Integer recipientsCount;
  private Integer emailsLeft;
  private String created;

  public String get_id() {
    return _id;
  }

  public EmailResponse set_id(String _id) {
    this._id = _id;
    return this;
  }

  public Integer getRecipientsCount() {
    return recipientsCount;
  }

  public EmailResponse setRecipientsCount(Integer recipientsCount) {
    this.recipientsCount = recipientsCount;
    return this;
  }

  public String getCreated() {
    return created;
  }

  public EmailResponse setCreated(String created) {
    this.created = created;
    return this;
  }

  public Integer getEmailsLeft() {
    return emailsLeft;
  }

  public EmailResponse setEmailsLeft(Integer emailsLeft) {
    this.emailsLeft = emailsLeft;
    return this;
  }
}
