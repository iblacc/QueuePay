package com.dev.QueuePay.user.repositories;

import com.dev.QueuePay.user.models.role.Role;
import com.dev.QueuePay.user.models.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleName name);
}
