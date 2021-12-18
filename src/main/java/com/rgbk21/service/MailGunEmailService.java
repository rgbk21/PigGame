package com.rgbk21.service;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import com.rgbk21.model.EmailResponse;
import com.rgbk21.utils.CommonUtils;
import com.rgbk21.utils.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MailGunEmailService {

  private final WebClient webClient;

  private final String ENV_KEY_MAILGUN_DOMAIN_NAME = "MAILGUN_DOMAIN";
  private final String ENV_KEY_MAILGUN_PUBLIC_KEY = "MAILGUN_PUBLIC_KEY";
  private final String ENV_KEY_MAILGUN_API_KEY = "MAILGUN_API_KEY";
  private final String ENV_KEY_RECIPIENT_EMAIL_ID = "EMAIL_ID";

  private final String MAIL_GUN_BASE_URL = "https://api.mailgun.net/v3/";
  private final String SUBJECT = "subject";
  private final String FROM = "from";
  private final String TO = "to";
  private final String TEXT = "text";
  private final String API = "api";

  public MailGunEmailService(WebClient.Builder builder) {
    this.webClient = builder.baseUrl(MAIL_GUN_BASE_URL).build();
  }

  public EmailResponse sendEmail_v3(String body) {
    String domainName = checkNotNull(CommonUtils.getEnvVariableValueForKey(ENV_KEY_MAILGUN_DOMAIN_NAME));
    String apiKey = checkNotNull(CommonUtils.getEnvVariableValueForKey(ENV_KEY_MAILGUN_API_KEY));
    String recipientEmailAddress = checkNotNull(CommonUtils.getEnvVariableValueForKey(ENV_KEY_RECIPIENT_EMAIL_ID));

    String subjectOfEmail = "New Game Request";
    String fromEmailAddress = "pig-game <piggamer@" + domainName + ">";
    String toEmailAddress = "gaurav <" + recipientEmailAddress + ">";
    String uriStr = domainName + "/messages";

    return webClient.post()
        .uri(uriBuilder -> uriBuilder
            .path(uriStr)
            .queryParam(SUBJECT, subjectOfEmail)
            .queryParam(FROM, fromEmailAddress)
            .queryParam(TO, toEmailAddress)
            .queryParam(TEXT, body)
            .build())
        .headers(httpHeaders -> httpHeaders.setBasicAuth(API, apiKey))
        .retrieve()
        .bodyToMono(EmailResponse.class)
        .block();
  }

  // TODO: There is a body() method that takes in a Mono and what not and then there is also a bodyToValue() method
  // that takes an Object type as input. Is that the thing that you were missing when you were running into the issue of 403 and
  // you had get around that by using the string directly?
  public EmailResponse sendEmail_v2(String body) {
    String domainName = checkNotNull(CommonUtils.getEnvVariableValueForKey(ENV_KEY_MAILGUN_DOMAIN_NAME));
    String apiKey = checkNotNull(CommonUtils.getEnvVariableValueForKey(ENV_KEY_MAILGUN_API_KEY));
    String recipientEmailAddress = checkNotNull(CommonUtils.getEnvVariableValueForKey(ENV_KEY_RECIPIENT_EMAIL_ID));

    String subjectOfEmail = "New Game Request";
    String fromEmailAddress = "pig-game <piggamer@" + domainName + ">";
    String toEmailAddress = "gaurav <" + recipientEmailAddress + ">";
    String uriStr = domainName + "/messages";
    return webClient.post()
        .uri(uriBuilder -> uriBuilder
            .path(uriStr)
            .queryParam(SUBJECT, subjectOfEmail)
            .queryParam(FROM, fromEmailAddress)
            .queryParam(TO, toEmailAddress)
            .queryParam(TEXT, body)
            .build())
        .headers(httpHeaders -> httpHeaders.setBasicAuth(API, apiKey))
        .headers(httpHeaders -> httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
        .headers(httpHeaders -> httpHeaders.setAccept(ImmutableList.of(MediaType.ALL)))
        .accept(MediaType.ALL)
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(""), String.class)
        .retrieve()
        .bodyToMono(EmailResponse.class)
        .block();
  }

  public EmailResponse sendEmail() {

//    String body = "{\"recipients\": [{\"email\": \"rajgaurav.bk@gmail.com\"}], \"title\": \""+ title +"\", \"html\": \""+ msgBody +"\"}";

    return webClient.post()
        .uri(MAIL_GUN_BASE_URL)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .header("x-trustifi-key", CommonUtils.getEnvVariableValueForKey(Constants.TRUSTIFI_KEY))
        .header("x-trustifi-secret", CommonUtils.getEnvVariableValueForKey(Constants.TRUSTIFI_SECRET))
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(""), String.class)
        .retrieve()
        .bodyToMono(EmailResponse.class)
        .block();
  }
}
