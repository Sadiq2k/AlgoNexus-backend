package com.algo.nexus.userService.Repository;

import com.algo.nexus.userService.Model.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByEmail(String email);

    Optional<User> findUserByEmail(String email);
    User findById(UUID userId);

    @Query(value = "SELECT COUNT(*) FROM user", nativeQuery = true)
    Long findTotalUserCount();
}