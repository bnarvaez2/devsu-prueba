package com.devsu.account.client.customer;

import com.devsu.account.client.customer.dto.CustomerResponse;

public interface CustomerClient {

  CustomerResponse getCustomerByIdentification(Long identification);
}
