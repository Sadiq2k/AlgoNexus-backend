package com.algo.nexus.userService.Repository;

import com.algo.nexus.userService.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByEmail(String email);

    Optional<User> findUserByEmail(String email);
    User findById(UUID userId);

}