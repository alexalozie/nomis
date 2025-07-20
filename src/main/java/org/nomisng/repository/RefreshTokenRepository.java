package org.nomisng.repository;

import org.nomisng.domain.entity.RefreshToken;
import org.nomisng.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    @Override
    Optional<RefreshToken> findById(UUID id);
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(User user);
}
