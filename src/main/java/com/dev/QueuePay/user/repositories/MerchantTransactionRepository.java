package com.dev.QueuePay.user.repositories;

import com.dev.QueuePay.user.models.MerchantTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantTransactionRepository extends JpaRepository<MerchantTransaction, Long> {
    List<MerchantTransaction> findAllByUserId(Long merchantId);

    @Query(value = "SELECT sum(m.amount) FROM MerchantTransaction m WHERE m.user.id = ?1 AND m.status = 0")
    Double getMerchantTransactionByAmount(Long merchantId);
}