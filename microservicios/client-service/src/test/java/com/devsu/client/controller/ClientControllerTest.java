package com.devsu.client.controller;

import com.devsu.client.repository.ClientRepository;
import com.devsu.client.repository.model.ClientEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

  @Autowired
  ClientRepository repository;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void getClientsTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/clientes")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  public void createClientTest() throws Exception {
    String json = "{"
      + "\"password\":\"testPassword\","
      + "\"identification\":1124,"
      + "\"firstName\":\"testFirstName\","
      + "\"lastName\":\"testLastName\","
      + "\"gender\":\"testGender\","
      + "\"age\":30,"
      + "\"address\":\"testAddress\","
      + "\"phoneNumber\":\"testPhoneNumber\","
      + "\"status\":true"
      + "}";

    mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
      .andExpect(MockMvcResultMatchers.status().isCreated())
      .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("Client was created."));
  }

  @Test
  public void getClientByIdentificationTest() throws Exception {
    repository.save(ClientEntity.builder().identification(1234L).status(true).build());

    mockMvc.perform(MockMvcRequestBuilders.get("/clientes/" + 1234L)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    repository.deleteAll();
  }
}
