package com.rgbk21.model;

public class EmailResponse {
  private String _id;
  private Integer recipientsCount;
  private Integer emailsLeft;

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

  public Integer getEmailsLeft() {
    return emailsLeft;
  }

  public EmailResponse setEmailsLeft(Integer emailsLeft) {
    this.emailsLeft = emailsLeft;
    return this;
  }
}
