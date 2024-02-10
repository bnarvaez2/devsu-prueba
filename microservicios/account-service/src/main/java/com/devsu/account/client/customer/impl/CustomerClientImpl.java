package com.devsu.account.client.customer.impl;

import com.devsu.account.client.customer.CustomerClient;
import com.devsu.account.client.customer.CustomerClientConfig;
import com.devsu.account.client.customer.dto.CustomerResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerClientImpl implements CustomerClient {

  private final RestTemplate restTemplate;

  public CustomerClientImpl(CustomerClientConfig config) {
    this.restTemplate = config.getRestTemplate();
  }

  @Override
  public CustomerResponse getCustomerByIdentification(Long identification) {
    return restTemplate.getForObject("/clientes/{identification}",
      CustomerResponse.class, identification);
  }
}
