package com.rgbk21.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Recipient {
  private String email;
  private String name;
  private Phone phone;

  public String getEmail() {
    return email;
  }

  public Recipient setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getName() {
    return name;
  }

  public Recipient setName(String name) {
    this.name = name;
    return this;
  }

  public Phone getPhone() {
    return phone;
  }

  public Recipient setPhone(Phone phone) {
    this.phone = phone;
    return this;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("email", email)
        .add("name", name)
        .add("phone", phone)
        .toString();
  }
}
