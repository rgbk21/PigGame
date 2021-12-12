package com.rgbk21.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Phone {
  private String country_code;
  private String phone_number;

  public String getCountry_code() {
    return country_code;
  }

  public Phone setCountry_code(String country_code) {
    this.country_code = country_code;
    return this;
  }

  public String getPhone_number() {
    return phone_number;
  }

  public Phone setPhone_number(String phone_number) {
    this.phone_number = phone_number;
    return this;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("country_code", country_code)
        .add("phone_number", phone_number)
        .toString();
  }
}
