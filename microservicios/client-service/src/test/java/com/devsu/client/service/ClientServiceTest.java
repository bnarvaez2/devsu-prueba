package com.devsu.client.service;

import com.devsu.client.dto.NewClientRequest;
import com.devsu.client.dto.UpdateClientRequest;
import com.devsu.client.repository.ClientRepository;
import com.devsu.client.repository.model.ClientEntity;
import com.devsu.client.service.exception.ClientAlreadyExistException;
import com.devsu.client.service.exception.ClientNotFoundException;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClientServiceTest {

  @Autowired
  private ClientService service;
  @Autowired
  private ClientRepository repository;

  @BeforeEach
  void init() {
    repository.deleteAll();
  }

  @Test
  public void verifyService() {
    assertNotNull(service);
  }

  @Test
  @DisplayName("createClient: insert a client and check if it was saved in database")
  public void createClient() {
    val request = NewClientRequest.builder().identification(1234L).build();

    service.createClient(request);

    assertTrue(repository.findByIdentificationAndStatusIsTrue(1234L).isPresent());
  }

  @Test
  @DisplayName("createClient: insert a client its was deleted")
  public void createClient_secondCase() {
    val request = NewClientRequest.builder().identification(1234L).build();
    insertClient(false);

    service.createClient(request);

    assertTrue(repository.findByIdentificationAndStatusIsTrue(1234L).isPresent());
  }

  @Test
  @DisplayName("createClient: given a client already exist then throw a conflict exception")
  public void createClient_throwConflictException() {
    val request = NewClientRequest.builder().identification(1234L).build();
    insertClient(true);

    assertThrows(ClientAlreadyExistException.class, () -> service.createClient(request));
  }

  @Test
  @DisplayName("updateClient: update a client and check if it was updated")
  public void updateClient() {
    String nameExpected = "Brian";
    val request = UpdateClientRequest.builder().firstName(nameExpected).build();
    insertClient(true);

    service.updateClient(1234L, request);

    assertEquals(nameExpected, repository.findByIdentification(1234L).get().getFirstName());
  }

  @Test
  @DisplayName("updateClient: given a non existing client then throw a NotFoundException")
  public void updateClient_throwNotFoundException() {
    String nameExpected = "Brian";
    val request = UpdateClientRequest.builder().firstName(nameExpected).build();

    assertThrows(ClientNotFoundException.class, () -> service.updateClient(123L, request));
  }

  @Test
  @DisplayName("deleteClient: delete a client and check if its status changed")
  public void deleteClient() {
    insertClient(true);

    service.deleteClient(1234L);

    assertTrue(repository.findByIdentification(1234L).isPresent());
    Assertions.assertFalse(repository.findByIdentification(1234L).get().isStatus());
  }

  @Test
  @DisplayName("deleteClient: given a non existing client then throw a NotFoundException")
  public void deleteClient_throwNotFoundException() {
    assertThrows(ClientNotFoundException.class, () -> service.deleteClient(123L));
  }

  @Test
  @DisplayName("deleteClient: given a client deleted then throw a NotFoundException")
  public void deleteClient_throwNotFoundException_secondCase() {
    insertClient(false);

    assertThrows(ClientNotFoundException.class, () -> service.deleteClient(123L));
  }

  @Test
  @DisplayName("getClients: return all actives client")
  public void getClients() {
    insertClient(true);

    val clients = service.getClients();

    assertFalse(clients.isEmpty());
  }

  @Test
  @DisplayName("getClients: return empty client list")
  public void getClients_secondCase() {
    insertClient(false);

    val clients = service.getClients();

    assertTrue(clients.isEmpty());
  }

  @Test
  @DisplayName("getClientByIdentification: given a identification then return client")
  public void getClient() {
    insertClient(true);

    val client = service.getClientByIdentification(1234L);

    assertNotNull(client);
  }

  @Test
  @DisplayName("getClientByIdentification: given a non existing identification then return NotFoundException")
  public void getClient_throwNotFoundException() {
    assertThrows(ClientNotFoundException.class, () -> service.getClientByIdentification(123L));
  }

  @Test
  @DisplayName("getClientByIdentification: given a client deleted then return NotFoundException")
  public void getClient_throwNotFoundException_secondCase() {
    insertClient(false);

    assertThrows(ClientNotFoundException.class, () -> service.getClientByIdentification(123L));
  }

  private void insertClient(boolean status) {
    repository.save(ClientEntity.builder().identification(1234L).status(status).build());
  }
}
