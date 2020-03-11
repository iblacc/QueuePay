package com.dev.QueuePay.services;

import com.dev.QueuePay.payload.PayOut;
import com.dev.QueuePay.payload.Performance;
import com.dev.QueuePay.user.models.MerchantTransaction;

import java.util.List;

public interface MerchantTransactionService {
    MerchantTransaction payout(Long merchantId,PayOut payout);
    List<MerchantTransaction> getAllTransactions(Long userId);
    Performance getPerfomance(Long userId);
    Double getTotalBalance(Long merchantId);
}
