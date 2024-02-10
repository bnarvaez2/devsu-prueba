package com.devsu.client.service.impl;

import com.devsu.client.dto.ClientResponse;
import com.devsu.client.dto.NewClientRequest;
import com.devsu.client.dto.UpdateClientRequest;
import com.devsu.client.repository.ClientRepository;
import com.devsu.client.service.ClientService;
import com.devsu.client.service.exception.ClientAlreadyExistException;
import com.devsu.client.service.exception.ClientNotFoundException;
import static com.devsu.client.service.mapper.ClientMapper.CLIENT_MAPPER;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

  private final ClientRepository repository;

  @Override
  public void createClient(NewClientRequest newClientRequest) {
    val verifyClient = repository.findByIdentification(newClientRequest.getIdentification());

    verifyClient.ifPresentOrElse(existingClient -> {
        if (existingClient.isStatus())
          throw new ClientAlreadyExistException();

        CLIENT_MAPPER.updateEntityFromRequest(newClientRequest, existingClient);
        repository.save(existingClient);
      },
      () -> repository.save(CLIENT_MAPPER.fromRequest(newClientRequest))
    );
  }

  @Override
  public void updateClient(long identification, UpdateClientRequest request) {
    val clientEntity = repository.findByIdentificationAndStatusIsTrue(identification)
      .orElseThrow(ClientNotFoundException::new);

    CLIENT_MAPPER.updateEntityFromRequest(request, clientEntity);

    repository.save(clientEntity);
  }

  @Override
  public void deleteClient(long identification) {
    val client = repository.findByIdentificationAndStatusIsTrue(identification)
      .orElseThrow(ClientNotFoundException::new);

    client.setStatus(false);
    repository.save(client);
  }

  @Override
  public List<ClientResponse> getClients() {
    val clients = repository.findAllByStatusIsTrue();

    return CLIENT_MAPPER.toResponse(clients);
  }

  @Override
  public ClientResponse getClientByIdentification(long identification) {
    val client = repository.findByIdentificationAndStatusIsTrue(identification)
      .orElseThrow(ClientNotFoundException::new);

    return CLIENT_MAPPER.toResponse(client);
  }
}
