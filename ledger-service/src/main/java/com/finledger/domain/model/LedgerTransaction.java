package com.finledger.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public final class LedgerTransaction {
    private final TransactionId id;
    private final List<TransactionEntry> entries;
    private final Instant timestamp;

    public LedgerTransaction(TransactionId id, List<TransactionEntry> entries, Instant timestamp) {
        if (id == null) {
            throw new IllegalArgumentException("Transaction ID cannot be null");
        }

        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }

        // At least 2 entries
        if (entries == null || entries.size() < 2) {
            throw new IllegalArgumentException("A transaction must have at least 2 entries");
        }

        String expectedCurrency = entries.getFirst().getCurrency();
        for (TransactionEntry entry : entries) {
            if (!entry.getCurrency().equals(expectedCurrency)) {
                throw new IllegalArgumentException("All entries in a transaction must have the same currency");
            }
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (TransactionEntry entry : entries) {
            sum = sum.add(entry.getAmount());
        }

        if (sum.compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("Entries must sum to zero");
        }

        this.id = id;
        this.entries = List.copyOf(entries);
        this.timestamp = timestamp;
    }

    public TransactionId getId() {
        return id;
    }

    public List<TransactionEntry> getEntries() {
        return entries;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}