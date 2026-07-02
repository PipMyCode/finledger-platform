package com.finledger.domain.model;

import java.math.BigDecimal;


public final class TransactionEntry {
    private final AccountId accountId;
    private final BigDecimal amount;
    private final String currency;

    public TransactionEntry(AccountId accountId, BigDecimal amount, String currency) {
        if (accountId == null) {
            throw new IllegalArgumentException("Account ID cannot be null");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Amount cannot be null or zero");
        }

        if (currency == null || currency.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency cannot be null or empty");
        }

        this.accountId = accountId;
        this.amount = amount;
        this.currency = currency;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
