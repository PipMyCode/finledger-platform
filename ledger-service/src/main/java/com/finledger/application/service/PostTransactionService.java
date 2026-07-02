package com.finledger.application.service;

import com.finledger.application.dto.TransactionRequest;
import com.finledger.application.ports.inbound.PostTransactionUseCase;
import com.finledger.application.ports.outbound.SaveLedgerPort;
import com.finledger.domain.model.AccountId;
import com.finledger.domain.model.LedgerTransaction;
import com.finledger.domain.model.TransactionEntry;
import com.finledger.domain.model.TransactionId;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PostTransactionService implements PostTransactionUseCase {
    private final SaveLedgerPort saveLedgerPort;

    public PostTransactionService(SaveLedgerPort saveLedgerPort) {
        this.saveLedgerPort = saveLedgerPort;
    }

    @Override
    public TransactionId postTransaction(TransactionRequest request) {
        System.out.println("Running on thread: " + Thread.currentThread());
        AccountId sourceAccountId = AccountId.of(UUID.fromString(request.sourceAccountId()));
        AccountId destinationId = AccountId.of(UUID.fromString(request.destinationAccountId()));

        TransactionEntry debitEntry = new TransactionEntry(sourceAccountId, request.amount().negate(), request.currency());
        TransactionEntry creditEntry = new TransactionEntry(destinationId, request.amount(), request.currency());

        LedgerTransaction ledgerTransaction = new LedgerTransaction(
                TransactionId.generate(),
                List.of(debitEntry, creditEntry),
                Instant.now()
        );

        saveLedgerPort.save(ledgerTransaction);

        return ledgerTransaction.getId();
    }

}


