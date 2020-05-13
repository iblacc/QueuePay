package com.dev.QueuePay.services;

import com.dev.QueuePay.payload.TransactionRequest;
import com.dev.QueuePay.user.models.CustomerTransaction;

import java.util.List;

public interface CustomerTransactionService {
    List<CustomerTransaction> getAllTransactions(Long merchantId);
    CustomerTransaction getTransaction(Long merchantId, Long id);
    CustomerTransaction setTransaction(Long merchantId, TransactionRequest transactionRequest);

}

