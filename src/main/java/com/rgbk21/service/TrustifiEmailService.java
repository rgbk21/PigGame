package com.rgbk21.service;

import static com.google.common.base.Preconditions.checkNotNull;

import com.rgbk21.model.EmailMessage;
import com.rgbk21.model.EmailResponse;
import com.rgbk21.utils.CommonUtils;
import com.rgbk21.utils.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TrustifiEmailService {

  private final WebClient webClient;
  private final String URI_SEND_EMAIL = "/api/i/v1/email";

  public TrustifiEmailService(WebClient.Builder builder) {
    String baseUrl = CommonUtils.getEnvVariable(Constants.TRUSTIFI_URL);
    checkNotNull(baseUrl);
    this.webClient = builder.baseUrl(baseUrl).build();
  }

  public EmailResponse sendEmail(EmailMessage emailMsgPayload) {

    String title = emailMsgPayload.getTitle();
    String msgBody = emailMsgPayload.getHtml();
//    String body = "{\"recipients\": [{\"email\": \"rajgaurav.bk@gmail.com\"}], \"title\": \"Title\", \"html\": \"Body\"}";
    String body = "{\"recipients\": [{\"email\": \"rajgaurav.bk@gmail.com\"}], \"title\": \""+ title +"\", \"html\": \""+ msgBody +"\"}";

    return webClient.post()
        .uri(URI_SEND_EMAIL)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .header("x-trustifi-key", CommonUtils.getEnvVariable(Constants.TRUSTIFI_KEY))
        .header("x-trustifi-secret", CommonUtils.getEnvVariable(Constants.TRUSTIFI_SECRET))
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(body), String.class)
        .retrieve()
        .bodyToMono(EmailResponse.class)
        .block();
  }
}
