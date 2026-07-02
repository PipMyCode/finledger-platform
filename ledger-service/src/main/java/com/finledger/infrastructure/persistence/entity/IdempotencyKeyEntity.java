package com.finledger.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "idempotency_keys")
@NoArgsConstructor
@Getter
@Setter
public class IdempotencyKeyEntity {

    @Id
    private String idempotencyKey;

    private Instant createdAt = Instant.now();

    public IdempotencyKeyEntity(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

}
