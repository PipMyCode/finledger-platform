package com.finledger.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "transaction_entries")
@NoArgsConstructor
@Getter
@Setter
public class TransactionEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID accountId;

    private BigDecimal amount;

    private String currency;

    @ManyToOne
    @JoinColumn(name = "ledger_transaction_id")
    private LedgerTransactionEntity ledgerTransaction;

}
