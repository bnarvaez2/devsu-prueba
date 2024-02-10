package com.devsu.account.service.mapper;

import com.devsu.account.dto.AccountResponse;
import com.devsu.account.dto.NewAccountRequest;
import com.devsu.account.repository.model.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface AccountMapper {

  AccountMapper ACCOUNT_MAPPER = Mappers.getMapper(AccountMapper.class);

  @Mapping(target = "currentBalance", source = "initialBalance")
  AccountEntity fromRequest(NewAccountRequest request);

  List<AccountResponse> toResponse(List<AccountEntity> clients);

  AccountResponse toResponse(AccountEntity client);
}
