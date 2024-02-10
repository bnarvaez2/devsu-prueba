package com.devsu.account.repository;

import com.devsu.account.repository.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

  Optional<AccountEntity> findByIdAndStatusIsTrue(Long accountId);

  List<AccountEntity> findAllByClientIdAndStatusIsTrue(Long clientId);

  List<AccountEntity> findAllByStatusIsTrue();

  List<AccountEntity> findAllBy();
}
