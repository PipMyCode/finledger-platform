package com.finledger.wallet.wallet_service.dto;

import java.math.BigDecimal;

public record EntryDto(
        IdWrapper accountId,
        BigDecimal amount,
        String currency
) {}
