package com.rgbk21.service;

import com.rgbk21.model.EmailResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceHelper {

  private static final Log LOGGER = LogFactory.getLog(EmailServiceHelper.class);

  @Autowired
  private MailGunEmailService emailService;

  public void sendEmail(String gameId) {
    EmailResponse emailResponse = emailService.sendEmail_v3(gameId);
    LOGGER.info("EmailService: sendEmail _id of sent email: " + emailResponse.getId());
  }
}
