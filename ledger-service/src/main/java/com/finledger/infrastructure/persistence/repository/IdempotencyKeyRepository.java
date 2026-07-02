package com.finledger.infrastructure.persistence.repository;

import com.finledger.infrastructure.persistence.entity.IdempotencyKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdempotencyKeyRepository extends JpaRepository<IdempotencyKeyEntity, String> {

    boolean existsById(String id);
}
