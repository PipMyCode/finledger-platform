package com.finledger.wallet.wallet_service.messaging;

import com.finledger.wallet.wallet_service.domain.AccountBalance;
import com.finledger.wallet.wallet_service.dto.EntryDto;
import com.finledger.wallet.wallet_service.dto.LedgerEventDto;
import com.finledger.wallet.wallet_service.repository.AccountBalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.math.BigDecimal;

@Component
@Slf4j
@RequiredArgsConstructor
public class LedgerEventListener {

    private final AccountBalanceRepository repository;
    private final JsonMapper jsonMapper;

    @KafkaListener(topics = "ledger-events", groupId = "wallet-group")
    public void consumeLedgerEvent(String messagePayload) throws Exception {

        LedgerEventDto event = jsonMapper.readValue(messagePayload, LedgerEventDto.class);

        for (EntryDto entry : event.entries()) {
            String accountId = entry.accountId().value().toString();

            AccountBalance accountBalance = repository.findById(accountId)
                    .orElse(new AccountBalance(accountId, BigDecimal.ZERO));


            accountBalance.setBalance(accountBalance.getBalance().add(entry.amount()));
            repository.save(accountBalance);

            log.info("Wallet Updated! Account: {} | New Balance: {}", accountId, accountBalance.getBalance());
        }

    }
}
