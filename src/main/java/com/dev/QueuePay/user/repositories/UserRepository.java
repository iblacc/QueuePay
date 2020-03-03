package com.dev.QueuePay.user.repositories;

import com.dev.QueuePay.user.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(@NotBlank String email);

    Boolean existsByEmail(@NotBlank String email);


}

