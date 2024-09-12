package net.branium.repositories;

import net.branium.constants.RoleEnum;
import net.branium.domains.Role;
import net.branium.domains.User;
import net.branium.exceptions.ApplicationException;
import net.branium.utils.RandomGenerateUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Description;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTests {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        Role roleStudent = Role.builder()
                .name(RoleEnum.ROLE_STUDENT.getName())
                .description(RoleEnum.ROLE_STUDENT.getDescription())
                .build();

        Role roleAdmin = Role.builder()
                .name(RoleEnum.ROLE_ADMIN.getName())
                .description(RoleEnum.ROLE_ADMIN.getDescription())
                .build();

        roleRepo.saveAll(Set.of(roleAdmin, roleStudent));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        user1 = User.builder()
                .username("phuonganhnguyen")
                .email("nphanh75@gmail.com")
                .password(passwordEncoder.encode("Sohappy212@"))
                .firstName("Phuong Anh")
                .lastName("Nguyen")
                .enabled(true)
                .gender(true)
                .birthDate(LocalDate.of(2003, Month.MAY, 7))
                .avatar(null)
                .vipLevel(0)
                .phoneNumber("0987666543")
                .verificationCode("123456789")
                .roles(Set.of(roleStudent))
                .build();

        user2 = User.builder()
                .username("maianhdo")
                .email("maianhdo@gmail.com")
                .password(passwordEncoder.encode("Sohappy212@"))
                .firstName("Phuong Anh")
                .lastName("Nguyen")
                .enabled(false)
                .gender(true)
                .birthDate(LocalDate.of(2003, Month.MAY, 7))
                .avatar(null)
                .vipLevel(0)
                .phoneNumber("0987666543")
                .verificationCode("123456784")
                .roles(Set.of(roleStudent))
                .build();

        userRepo.saveAll(List.of(user1, user2));
    }

    @DisplayName("Test get user by email is successful")
    @Test
    public void givenExistedEmail_whenFindByEmail_thenReturnUserEnabledObject() {
        User userByEmail = userRepo.findByEmail(user1.getEmail()).orElse(null);
        Assertions.assertThat(userByEmail).isNotNull();
    }

    @DisplayName("Test get user by email is failed because user not enabled")
    @Test
    public void givenExistedEmail_whenFindByEmail_thenReturnUserDisabledObject() {
        User userByEmail = userRepo.findByEmail(user2.getEmail()).orElse(null);
        Assertions.assertThat(userByEmail).isNull();
    }

    @DisplayName("Test get user by email is failed because user's email not existed")
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

    @DisplayName("Test get user by verification code is failed because user not enabled")
    @Test
    public void givenExistedVerificationCode_whenFindByVerificationCode_thenReturnUserDisabledObject() {
        User userByVerificationCode = userRepo.findByVerificationCode(user2.getVerificationCode()).orElse(null);
        Assertions.assertThat(userByVerificationCode).isNull();
    }

    @DisplayName("Test get user by verification code is failed because user's verification code not existed")
    @Test
    public void givenNonExistedVerificationCode_whenFindByVerificationCode_thenReturnNullObject() {
        String nonVerificationCode = "invalid";
        User userByVerificationCode = userRepo.findByVerificationCode(nonVerificationCode).orElse(null);
        Assertions.assertThat(userByVerificationCode).isNull();
    }



}
