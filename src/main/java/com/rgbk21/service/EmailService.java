package com.rgbk21.service;

import com.rgbk21.model.EmailMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailService {

  private static final Log LOGGER = LogFactory.getLog(EmailService.class);
  private static final String TRUSTIFI_KEY = "TRUSTIFI_KEY";
  private static final String TRUSTIFI_SECRET = "TRUSTIFI_SECRET";
  private static final String TRUSTIFI_URL = "TRUSTIFI_URL";
  private static final String EMAIL_ID = "EMAIL_ID";

  public void sendEmail(String gameId) {
    // Get the key, secret, url, emailId from the env
    String key = "";
    String secret = "";
    String url = "";
    String recipientEmailId = "";

    Map<String, String> env = System.getenv();

    for (String name : env.keySet()) {
      String value = env.get(name);
      switch (name) {
        case TRUSTIFI_KEY:
          key = value;
          break;
        case TRUSTIFI_SECRET:
          secret = value;
          break;
        case TRUSTIFI_URL:
          url = value;
          break;
        case EMAIL_ID:
          recipientEmailId = value;
          break;
      }
    }
    LOGGER.info("EmailService::sendEmail key is: " + key);
    LOGGER.info("EmailService::sendEmail secret is: " + secret);
    LOGGER.info("EmailService::sendEmail url is: " + url);
    LOGGER.info("EmailService::sendEmail recipientEmailId is: " + recipientEmailId);
  }
}
