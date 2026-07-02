package com.finledger.application.ports.outbound;

import com.finledger.domain.model.LedgerTransaction;

public interface SaveLedgerPort {

    void save(LedgerTransaction transaction);
}
