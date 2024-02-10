package com.devsu.account.service.mapper;

import com.devsu.account.client.customer.dto.CustomerResponse;
import com.devsu.account.dto.AccountInfo;
import com.devsu.account.dto.AccountMovementInfo;
import com.devsu.account.dto.AccountResponse;
import com.devsu.account.dto.ReportResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface ReportMapper {

  ReportMapper REPORT_MAPPER = Mappers.getMapper(ReportMapper.class);

  @Mapping(target = "accountId", source = "account.id")
  @Mapping(target = "type", source = "account.type")
  @Mapping(target = "status", source = "account.status")
  @Mapping(target = "currentBalance", expression = "java( movements.isEmpty() ? account.getCurrentBalance() : movements.get(0).getBalance() )")
  @Mapping(target = "movements", source = "movements")
  AccountInfo toAccountInfo(AccountResponse account, List<AccountMovementInfo> movements);

  @Mapping(target = "identification", source = "client.identification")
  @Mapping(target = "client", expression = "java( client.getFirstName() + \" \" + client.getLastName() )")
  @Mapping(target = "accounts", source = "accountInfoList")
  ReportResponse toReportResponse(CustomerResponse client, List<AccountInfo> accountInfoList);
}
