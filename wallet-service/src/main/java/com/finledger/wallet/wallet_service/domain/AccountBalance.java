package com.finledger.wallet.wallet_service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;

@RedisHash("AccountBalance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalance {
    @Id
    private String accountId;

    private BigDecimal balance;
}
