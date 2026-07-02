package com.finledger.domain.model;

import java.util.UUID;

public final class TransactionId {
    private final UUID value;

    public TransactionId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("Transaction ID value cannot be null");
        }
        this.value = value;
    }

    public static TransactionId generate() {
        return new TransactionId(UUID.randomUUID());
    }

    public static TransactionId of(UUID value) {
        return new TransactionId(value);
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionId that)) return false;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
