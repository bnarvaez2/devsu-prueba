package com.devsu.client.service.mapper;

import com.devsu.client.dto.ClientResponse;
import com.devsu.client.dto.NewClientRequest;
import com.devsu.client.dto.UpdateClientRequest;
import com.devsu.client.repository.model.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface ClientMapper {

  ClientMapper CLIENT_MAPPER = Mappers.getMapper(ClientMapper.class);

  ClientEntity fromRequest(NewClientRequest request);

  void updateEntityFromRequest(NewClientRequest dto, @MappingTarget ClientEntity entity);

  void updateEntityFromRequest(UpdateClientRequest dto, @MappingTarget ClientEntity entity);

  List<ClientResponse> toResponse(List<ClientEntity> clients);

  @Mapping(target = "identification", expression = "java(client.getIdentification())")
  ClientResponse toResponse(ClientEntity client);
}
