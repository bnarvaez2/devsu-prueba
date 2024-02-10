package com.devsu.account.service.mapper;

import com.devsu.account.dto.AccountMovementInfo;
import com.devsu.account.dto.AccountMovementResponse;
import com.devsu.account.dto.NewMovementRequest;
import com.devsu.account.repository.model.AccountMovementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface AccountMovementMapper {

  AccountMovementMapper MOVEMENT_MAPPER = Mappers.getMapper(AccountMovementMapper.class);

  @Mapping(target = "balance", expression = "java(balance)")
  AccountMovementEntity fromRequest(NewMovementRequest request, Double balance);

  List<AccountMovementResponse> fromEntity(List<AccountMovementEntity> entities);

  List<AccountMovementInfo> fromEntities(List<AccountMovementEntity> entities);
}
