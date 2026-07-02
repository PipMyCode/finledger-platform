package com.finledger.gateway.ingestion_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionRequest(
        @NotBlank
        String sourceAccountId,

        @NotBlank
        String destinationAccountId,

        @NotNull
        @Positive
        BigDecimal amount,

        @NotBlank
        String currency
) {
}
