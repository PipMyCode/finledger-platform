package com.finledger.wallet.wallet_service.dto;

import java.time.Instant;
import java.util.List;

public record LedgerEventDto(
        IdWrapper id,
        List<EntryDto> entries,
        Instant timestamp
) {}
