package com.example.APPbility.user.repository;

import com.example.APPbility.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findFirstByUsername(String username);

    Optional<User> findByActivationToken(String activationToken);

}
