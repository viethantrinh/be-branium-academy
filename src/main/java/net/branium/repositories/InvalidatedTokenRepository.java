package net.branium.repositories;

import net.branium.domains.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
    @Transactional
    List<InvalidatedToken> deleteByExpirationTimeBefore(Date expirationTime);
}
