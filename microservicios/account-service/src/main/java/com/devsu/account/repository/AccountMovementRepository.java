package com.devsu.account.repository;

import com.devsu.account.repository.model.AccountMovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AccountMovementRepository extends JpaRepository<AccountMovementEntity, UUID> {

  List<AccountMovementEntity> findAllByAccountId(Long accountId);

  List<AccountMovementEntity> findByAccountIdAndMovementDateBetweenOrderByMovementDateDesc(Long accountId, OffsetDateTime startDate, OffsetDateTime endDate);
}
