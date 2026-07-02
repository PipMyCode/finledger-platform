package com.finledger.wallet.wallet_service.repository;

import com.finledger.wallet.wallet_service.domain.AccountBalance;
import org.springframework.data.repository.CrudRepository;

public interface AccountBalanceRepository extends CrudRepository<AccountBalance, String> {
}
