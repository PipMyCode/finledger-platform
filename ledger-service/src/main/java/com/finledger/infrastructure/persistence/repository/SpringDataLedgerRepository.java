package com.finledger.infrastructure.persistence.repository;

import com.finledger.infrastructure.persistence.entity.LedgerTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataLedgerRepository extends JpaRepository<LedgerTransactionEntity, UUID> {
}
