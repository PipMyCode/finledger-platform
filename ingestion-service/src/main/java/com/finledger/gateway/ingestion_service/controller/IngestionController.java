package com.finledger.gateway.ingestion_service.controller;

import com.finledger.gateway.ingestion_service.dto.TransactionRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.core.JacksonException;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ingest")
public class IngestionController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JsonMapper jsonMapper;

    public IngestionController(KafkaTemplate<String, String> kafkaTemplate, JsonMapper jsonMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonMapper = jsonMapper;
    }

    @PostMapping("/transactions")
    public ResponseEntity<String> ingestTransaction(@Valid @RequestBody TransactionRequest request) {
        String clientTransactionId = UUID.randomUUID().toString();
        
        String payload;
        try {
            payload = jsonMapper.writeValueAsString(request);
        } catch (JacksonException e) {
            return ResponseEntity.internalServerError().body("Failed to serialize request");
        }

        kafkaTemplate.send("transaction-requests", clientTransactionId, payload);

        return ResponseEntity.accepted().body("Transaction accepted for processing. ID: " + clientTransactionId);
    }
}
