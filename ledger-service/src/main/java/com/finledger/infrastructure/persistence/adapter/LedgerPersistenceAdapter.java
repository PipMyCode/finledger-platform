package com.finledger.infrastructure.persistence.adapter;

import com.finledger.application.ports.outbound.SaveLedgerPort;
import com.finledger.domain.model.LedgerTransaction;
import com.finledger.domain.model.TransactionEntry;
import com.finledger.infrastructure.persistence.entity.LedgerTransactionEntity;
import com.finledger.infrastructure.persistence.entity.OutboxEventEntity;
import com.finledger.infrastructure.persistence.entity.TransactionEntryEntity;
import com.finledger.infrastructure.persistence.repository.OutboxEventRepository;
import com.finledger.infrastructure.persistence.repository.SpringDataLedgerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.core.JacksonException;

import java.util.List;

@Component
public class LedgerPersistenceAdapter implements SaveLedgerPort {

    private final SpringDataLedgerRepository ledgerRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final JsonMapper jsonMapper;

    public LedgerPersistenceAdapter(SpringDataLedgerRepository ledgerRepository,
                                    OutboxEventRepository outboxEventRepository,
                                    JsonMapper jsonMapper) {
        this.ledgerRepository = ledgerRepository;
        this.outboxEventRepository = outboxEventRepository;
        this.jsonMapper = jsonMapper;
    }

    @Override
    @Transactional
    public void save(LedgerTransaction transaction) {
        LedgerTransactionEntity entity = toEntity(transaction);
        ledgerRepository.save(entity);

        String payload;
        try {
            payload = jsonMapper.writeValueAsString(transaction);
        } catch (JacksonException e) {
            throw new RuntimeException("Failed to serialize transaction for outbox", e);
        }

        OutboxEventEntity outboxEvent = new OutboxEventEntity();
        outboxEvent.setAggregateId(transaction.getId().getValue());
        outboxEvent.setEventType("LedgerTransactionCreated");
        outboxEvent.setPayload(payload);

        outboxEventRepository.save(outboxEvent);
    }

    private LedgerTransactionEntity toEntity(LedgerTransaction transaction) {
        LedgerTransactionEntity entity = new LedgerTransactionEntity();
        entity.setId(transaction.getId().getValue());
        entity.setTimestamp(transaction.getTimestamp());

        List<TransactionEntryEntity> entryEntities = transaction.getEntries().stream()
                .map(entry -> toEntryEntity(entry, entity))
                .toList();

        entity.setEntries(entryEntities);
        return entity;
    }

    private TransactionEntryEntity toEntryEntity(TransactionEntry entry, LedgerTransactionEntity parent) {
        TransactionEntryEntity entryEntity = new TransactionEntryEntity();
        entryEntity.setAccountId(entry.getAccountId().getValue());
        entryEntity.setAmount(entry.getAmount());
        entryEntity.setCurrency(entry.getCurrency());
        entryEntity.setLedgerTransaction(parent);
        return entryEntity;
    }
}
