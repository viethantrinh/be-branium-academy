package net.branium.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class InvalidatedTokenRepositoryTests {

    @Autowired
    InvalidatedTokenRepository invalidatedTokenRepo;

    @Test
    void testDeleteAllInvalidatedTokenExpired() {
        invalidatedTokenRepo.deleteByExpirationTimeBefore(new Date());
    }
}