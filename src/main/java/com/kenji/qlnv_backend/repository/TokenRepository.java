package com.kenji.qlnv_backend.repository;

import com.kenji.qlnv_backend.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByRefreshToken(String refreshToken);
    void deleteByRefreshToken(String refreshToken);
    Optional<Token> findByUsername(String username);
    void deleteByUsername(String username);
    Optional<Token> findByJwtId(String jwtId);
}
