package com.devsu.client.repository;

import com.devsu.client.repository.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {

  Optional<ClientEntity> findByIdentification(Long identification);

  Optional<ClientEntity> findByIdentificationAndStatusIsTrue(Long identification);

  List<ClientEntity> findAllByStatusIsTrue();
}
