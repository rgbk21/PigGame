package com.rgbk21.service;

import static com.google.common.base.Preconditions.checkNotNull;

import com.rgbk21.model.EmailMessage;
import com.rgbk21.utils.CommonUtils;
import com.rgbk21.utils.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TrustifiEmailService {

  private final WebClient webClient;

  public TrustifiEmailService(WebClient.Builder builder) {
    String baseUrl = CommonUtils.getEnvVariable(Constants.TRUSTIFI_URL);
    checkNotNull(baseUrl);
    this.webClient = builder.baseUrl(baseUrl).build();
  }

  public void sendEmail(EmailMessage emailMsgPayload) {

    WebClient.ResponseSpec retrieve = webClient.post()
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .header(Constants.TRUSTIFI_KEY, CommonUtils.getEnvVariable(Constants.TRUSTIFI_URL))
        .header(Constants.TRUSTIFI_SECRET, CommonUtils.getEnvVariable(Constants.TRUSTIFI_SECRET))
        .body(Mono.just(emailMsgPayload), EmailMessage.class)
        .retrieve();
  }

}
