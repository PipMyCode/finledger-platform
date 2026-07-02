package com.finledger.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LedgerTransactionTest {

    @Test
    void shouldCreateTransactionWhenEntriesAreValid() {
        TransactionEntry debit = new TransactionEntry(AccountId.generate(), new BigDecimal("-100.00"), "NGN");
        TransactionEntry credit = new TransactionEntry(AccountId.generate(), new BigDecimal("100.00"), "NGN");

        LedgerTransaction transaction = new LedgerTransaction(
                TransactionId.generate(),
                List.of(debit, credit),
                Instant.now()
        );

        assertEquals(2, transaction.getEntries().size());
    }

    @Test
    void shouldThrowWhenFewerThanTwoEntries() {
        TransactionEntry onlyOne = new TransactionEntry(AccountId.generate(), new BigDecimal("-100.00"), "NGN");

        assertThrows(IllegalArgumentException.class, () -> {
            new LedgerTransaction(
                    TransactionId.generate(),
                    List.of(onlyOne),
                    Instant.now()
            );
        });
    }

    @Test
    void shouldThrowWhenCurrenciesDoNotMatch() {
        TransactionEntry ngnEntry = new TransactionEntry(AccountId.generate(), new BigDecimal("-100.00"), "NGN");
        TransactionEntry usdEntry = new TransactionEntry(AccountId.generate(), new BigDecimal("100.00"), "USD");

        assertThrows(IllegalArgumentException.class, () -> {
            new LedgerTransaction(
                    TransactionId.generate(),
                    List.of(ngnEntry, usdEntry),
                    Instant.now()
            );
        });
    }

    @Test
    void shouldThrowWhenEntriesDoNotSumToZero() {
        TransactionEntry debit = new TransactionEntry(AccountId.generate(), new BigDecimal("-100.00"), "NGN");
        TransactionEntry credit = new TransactionEntry(AccountId.generate(), new BigDecimal("200.00"), "NGN");

        assertThrows(IllegalArgumentException.class, () -> {
            new LedgerTransaction(
                    TransactionId.generate(),
                    List.of(debit, credit),
                    Instant.now()
            );
        });
    }
}
