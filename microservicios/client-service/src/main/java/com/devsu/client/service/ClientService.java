package com.devsu.client.service;

import com.devsu.client.dto.ClientResponse;
import com.devsu.client.dto.NewClientRequest;
import com.devsu.client.dto.UpdateClientRequest;
import java.util.List;

public interface ClientService {

  void createClient(NewClientRequest newClientRequest);

  void updateClient(long identification, UpdateClientRequest request);

  void deleteClient(long identification);

  List<ClientResponse> getClients();

  ClientResponse getClientByIdentification(long identification);
}
