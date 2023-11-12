package com.shk.userservice.repositories;

import com.shk.userservice.models.Role;
import com.shk.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    User findByRole(Role role);
}
