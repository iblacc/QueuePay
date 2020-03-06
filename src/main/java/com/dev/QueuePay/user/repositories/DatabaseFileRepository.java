package com.dev.QueuePay.user.repositories;

import com.dev.QueuePay.user.models.document.DatabaseFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseFileRepository extends JpaRepository<DatabaseFile, String> {
//    Optional<DatabaseFile> findById(String id);
}
