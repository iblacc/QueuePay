package com.dev.QueuePay.user.repositories;

import com.dev.QueuePay.user.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Boolean existsByEmail(String email);

    User findByVerifyEmailToken(String token);

    User findByUserId(UUID uuid);


}

