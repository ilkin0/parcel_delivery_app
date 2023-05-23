package com.ilkinmehdiyev.parceldelivery.usermanagement.repository;

import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.model.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
}
