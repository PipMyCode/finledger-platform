package com.finledger.presentation;

import com.finledger.application.dto.TransactionRequest;
import com.finledger.application.ports.inbound.PostTransactionUseCase;
import com.finledger.infrastructure.persistence.entity.IdempotencyKeyEntity;
import com.finledger.infrastructure.persistence.repository.IdempotencyKeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class IngestionMessageListener {

    private final PostTransactionUseCase postTransactionUseCase;
    private final JsonMapper jsonMapper;
    private final IdempotencyKeyRepository idempotencyKeyRepository;

    @KafkaListener(topics = "transaction-requests", groupId = "ledger-group")
    public void consumeTransactionRequest(@org.springframework.messaging.handler.annotation.Header(KafkaHeaders.RECEIVED_KEY) String idempotencyKey,
                                          String messagePayload) throws Exception {
        if (idempotencyKeyRepository.existsById(idempotencyKey)) {
            log.info("Idempotency key already exists, skipping transaction: {}", idempotencyKey);
            return;
        }
        idempotencyKeyRepository.save(new IdempotencyKeyEntity(idempotencyKey));

        log.info("Received fresh Kafka payload: {}", messagePayload);

        TransactionRequest request = jsonMapper.readValue(
                messagePayload,
                TransactionRequest.class);

        postTransactionUseCase.postTransaction(request);

        log.info("Successfully processed Kafka transaction for source account: {}", request.sourceAccountId());
    }
}
