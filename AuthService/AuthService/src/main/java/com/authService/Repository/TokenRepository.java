package com.authService.Repository;

import com.authService.Entities.Role;
import com.authService.Entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token,Integer> {
    Optional<Token> findByToken(String token);


}
