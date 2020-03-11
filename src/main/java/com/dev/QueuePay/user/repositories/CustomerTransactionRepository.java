package com.dev.QueuePay.user.repositories;

import com.dev.QueuePay.user.models.CustomerTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerTransactionRepository extends JpaRepository<CustomerTransaction, Long> {
    Page<CustomerTransaction> findByCustomerName(String name, Pageable pageable);
    List<CustomerTransaction> findByUserId(Long merchantId);
    List<CustomerTransaction> findByStatus(String status);

    @Query(value = "SELECT sum(c.amount) FROM CustomerTransaction c WHERE c.user.id = ?1 AND c.status = 0")
    Double getCustomerTransactionByAmount(Long merchantId);

}