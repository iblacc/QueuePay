package com.dev.QueuePay.user.repositories;

import com.dev.QueuePay.user.models.document.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {
}
