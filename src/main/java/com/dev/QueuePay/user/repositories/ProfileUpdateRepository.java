package com.dev.QueuePay.user.repositories;

import com.dev.QueuePay.user.models.document.ProfileUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileUpdateRepository extends JpaRepository<ProfileUpdate, String> {
//    Optional<DatabaseFile> findById(String id);
}
