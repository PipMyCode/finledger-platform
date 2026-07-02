package com.finledger.infrastructure.messaging;

import com.finledger.infrastructure.persistence.entity.OutboxEventEntity;
import com.finledger.infrastructure.persistence.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxRelayService {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 2000)
    public void relayOutboxEvents() {
        List<OutboxEventEntity> unprocessedEvents = outboxEventRepository.findTopByProcessedFalseOrderByCreatedAtAsc();

        for (OutboxEventEntity event : unprocessedEvents) {
            kafkaTemplate.send("ledger-events", event.getAggregateId().toString(), event.getPayload());
            event.setProcessed(true);
            outboxEventRepository.save(event);
        }
    }
}
