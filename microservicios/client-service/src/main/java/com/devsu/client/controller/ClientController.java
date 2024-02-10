package com.devsu.client.controller;

import static com.devsu.client.commom.util.ResponseUtil.response;
import com.devsu.client.dto.ClientResponse;
import com.devsu.client.dto.GenericResponse;
import com.devsu.client.dto.UpdateClientRequest;
import com.devsu.client.service.ClientService;
import com.devsu.client.dto.NewClientRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientController {

  private final ClientService service;

  @PostMapping
  public ResponseEntity<GenericResponse> createClient(@Valid @RequestBody NewClientRequest request) {
    service.createClient(request);

    return response("Client was created.", HttpStatus.CREATED);
  }

  @GetMapping
  public List<ClientResponse> getClients() {
    return service.getClients();
  }

  @GetMapping("/{identification}")
  public ClientResponse getClientByIdentification(@PathVariable(value = "identification") long identification) {
    return service.getClientByIdentification(identification);
  }

  @PutMapping
  public ResponseEntity<GenericResponse> updateClient(@RequestParam Long identification, @Valid @RequestBody UpdateClientRequest request) {
    service.updateClient(identification, request);

    return response("Client was updated.", HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<GenericResponse> deleteClient(@RequestParam Long identification) {
    service.deleteClient(identification);

    return response("Client was deleted.", HttpStatus.OK);
  }
}
