package com.rgbk21.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SMSMessagingService {

  private static final Log LOGGER = LogFactory.getLog(SMSMessagingService.class);
  private static final String TILL_URL = "TILL_URL";

  public void sendSMS() {
    String tillUrl = "";
    Map<String, String> env = System.getenv();
    for (String name : env.keySet()) {
      if (name.equals(TILL_URL)) {
        tillUrl = env.get(name);
      }
    }
    LOGGER.info("SMSMessagingService::sendSMS tillUrl is: " + tillUrl);
  }
}
