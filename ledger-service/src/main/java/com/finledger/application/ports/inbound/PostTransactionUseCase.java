package com.finledger.application.ports.inbound;

import com.finledger.application.dto.TransactionRequest;
import com.finledger.domain.model.TransactionId;

public interface PostTransactionUseCase {

    TransactionId postTransaction(TransactionRequest request);
}
