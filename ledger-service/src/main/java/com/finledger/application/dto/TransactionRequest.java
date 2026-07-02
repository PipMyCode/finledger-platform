package com.finledger.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionRequest (
        @NotBlank(message = "Source account is required")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "Invalid UUID format")
        String sourceAccountId,

        @NotBlank(message = "Destination account is required")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "Invalid UUID format")
        String destinationAccountId,

        @NotNull(message = "Amount is required")
        @Positive(message = "Transfer amount must be positive")
        BigDecimal amount,

        @NotBlank(message = "Currency is required")
        String currency
){}

