package com.devsu.account.client.customer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Data
@Component
@ConfigurationProperties("config.clients.client")
public class CustomerClientConfig {
  private String baseUrl;

  public RestTemplate getRestTemplate() {
    RestTemplate restTemplate = new RestTemplate();

    if (baseUrl != null) {
      DefaultUriBuilderFactory uriTemplateHandler = new DefaultUriBuilderFactory(baseUrl);
      restTemplate.setUriTemplateHandler(uriTemplateHandler);
    }

    restTemplate.getInterceptors().add((request, body, execution) -> {
      request.getHeaders().addIfAbsent("Content-Type", MediaType.APPLICATION_JSON_VALUE);
      return execution.execute(request, body);
    });

    return restTemplate;
  }
}
