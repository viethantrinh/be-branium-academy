package net.branium.repositories;

import net.branium.constants.RoleEnum;
import net.branium.domains.Role;
import net.branium.domains.User;
import net.branium.exceptions.ApplicationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.Month;
import java.util.NoSuchElementException;
import java.util.Set;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTests {

    @Autowired
    UserRepository userRepo;
    @Autowired
    RoleRepository roleRepo;
    Role roleStudent;
    Role roleAdmin;
    User user;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {

        roleStudent = Role.builder()
                .name(RoleEnum.ROLE_STUDENT.getName())
                .description(RoleEnum.ROLE_STUDENT.getDescription())
                .build();

        roleAdmin = Role.builder()
                .name(RoleEnum.ROLE_ADMIN.getName())
                .description(RoleEnum.ROLE_ADMIN.getDescription())
                .build();

        roleRepo.saveAll(Set.of(roleAdmin, roleStudent));

        passwordEncoder = new BCryptPasswordEncoder();

        user = User.builder()
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
                .roles(Set.of(roleStudent))
                .build();
        userRepo.save(user);
    }

    @Test
    public void givenExistedEmail_whenFindByEmail_thenReturnUserObject() {
        User userByEmail = userRepo.findByEmail(user.getEmail()).get();
        Assertions.assertThat(userByEmail).isNotNull();
    }

    @Test
    public void givenNonExistedEmail_whenFindByEmail_thenThrowNoSuchElementException() {
        String nonExistedEmail = "nphanhXX@gmail.com";
        org.junit.jupiter.api.Assertions.assertThrows(
                NoSuchElementException.class,
                () -> userRepo.findByEmail(nonExistedEmail).get()
        );
    }
}
