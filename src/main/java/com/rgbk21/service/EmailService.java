package com.rgbk21.service;

import com.rgbk21.model.EmailMessage;
import com.rgbk21.utils.CommonUtils;
import com.rgbk21.utils.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

  private static final Log LOGGER = LogFactory.getLog(EmailService.class);

  @Autowired private TrustifiEmailService emailService;

  public void sendEmail(String gameId) {
    // Get the key, secret, url, emailId from the env
    String key = CommonUtils.getEnvVariable(Constants.TRUSTIFI_KEY);
    String secret = CommonUtils.getEnvVariable(Constants.TRUSTIFI_SECRET);
    String url = CommonUtils.getEnvVariable(Constants.TRUSTIFI_URL);
    String recipientEmailId = CommonUtils.getEnvVariable(Constants.EMAIL_ID);

    LOGGER.info("EmailService::sendEmail key is: " + key);
    LOGGER.info("EmailService::sendEmail secret is: " + secret);
    LOGGER.info("EmailService::sendEmail url is: " + url);
    LOGGER.info("EmailService::sendEmail recipientEmailId is: " + recipientEmailId);

    EmailMessage emailMessage = new EmailMessage()
        .setTitle("New Game Request")
        .setHtml("Game Id: " + gameId)
        .addRecipient(CommonUtils.getEnvVariable(Constants.EMAIL_ID));

    emailService.sendEmail(emailMessage);
  }
}
