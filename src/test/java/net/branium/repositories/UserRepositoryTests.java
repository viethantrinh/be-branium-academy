package net.branium.repositories;

import net.branium.domains.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTests {

    @Autowired
    private UserRepository userRepo;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user1 = User.builder()
                .email("nphanh75@gmail.com")
                .verificationCode("123456789")
                .password(passwordEncoder.encode("Sohappy212@"))
                .build();

        user2 = User.builder()
                .email("maianhdo@gmail.com")
                .password(passwordEncoder.encode("Sohappy212@"))
                .verificationCode("123456784")
                .build();

        userRepo.saveAll(List.of(user1, user2));
    }

    @DisplayName("Test get user by email is successful")
    @Test
    public void givenExistedEmail_whenFindByEmail_thenReturnUserEnabledObject() {
        User userByEmail = userRepo.findByEmail(user1.getEmail()).orElse(null);
        Assertions.assertThat(userByEmail).isNotNull();
    }

    @DisplayName("Test get user by email is failed because email not existed")
    @Test
    public void givenNonExistedEmail_whenFindByEmail_thenReturnNullObject() {
        String nonExistedEmail = "nphanhXX@gmail.com";
        User userByEmail = userRepo.findByEmail(nonExistedEmail).orElse(null);
        Assertions.assertThat(userByEmail).isNull();
    }

    @DisplayName("Test get user by verification code is successful")
    @Test
    public void givenExistedVerificationCode_whenFindVerificationCode_thenReturnUserEnabledObject() {
        User userByVerificationCode = userRepo.findByVerificationCode(user1.getVerificationCode()).orElse(null);
        Assertions.assertThat(userByVerificationCode).isNotNull();
    }

    @DisplayName("Test get user by verification code is failed because user's verification code not existed")
    @Test
    public void givenNonExistedVerificationCode_whenFindByVerificationCode_thenReturnNullObject() {
        String nonVerificationCode = "invalid";
        User userByVerificationCode = userRepo.findByVerificationCode(nonVerificationCode).orElse(null);
        Assertions.assertThat(userByVerificationCode).isNull();
    }

    @DisplayName("Test if user is existed by email")
    @Test
    public void givenExistedEmail_whenCheckExistence_thenReturnTrue() {
        String email = "nphanh75@gmail.com";
        boolean isExisted = userRepo.existsByEmail(email);
        Assertions.assertThat(isExisted).isTrue();
    }

    @DisplayName("Test if user not existed by email")
    @Test
    public void givenNonExistedEmail_whenCheckExistence_thenReturnTrue() {
        String email = "nonExistedEmail@gmail.com";
        boolean isExisted = userRepo.existsByEmail(email);
        Assertions.assertThat(isExisted).isFalse();
    }



}
