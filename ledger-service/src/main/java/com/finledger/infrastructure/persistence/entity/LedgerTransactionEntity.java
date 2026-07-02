package com.finledger.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ledger_transactions")
@Getter
@Setter
@NoArgsConstructor
public class LedgerTransactionEntity {

    @Id
    private UUID id;

    private Instant timestamp;

    @OneToMany(mappedBy = "ledgerTransaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionEntryEntity> entries;


}
