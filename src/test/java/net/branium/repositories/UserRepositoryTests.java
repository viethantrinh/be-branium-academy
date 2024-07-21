package net.branium.repositories;

import lombok.RequiredArgsConstructor;
import net.branium.domains.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Rollback(value = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTests {

    @Autowired
    UserRepository userRepo;

    @Test
    void testSaveUserSuccess() throws IOException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        InputStream inputStream = getClass().getResourceAsStream("/static/images/viethantrinh.jpg");
        byte[] byteArrayAvatar = null;
        if (inputStream != null) byteArrayAvatar = inputStream.readAllBytes();
        User user = User
                .builder()
                .username("viethantrinh")
                .email("hntrnn12@gmail.com")
                .password(passwordEncoder.encode("Sohappy212@"))
                .firstName("Viet Han")
                .lastName("Trinh")
                .enabled(true)
                .gender(true)
                .birthDate(LocalDate.of(2003, Month.DECEMBER, 2))
                .avatar(byteArrayAvatar)
                .vipLevel(9999)
                .phoneNumber("0768701056")
                .build();
        User savedUser = userRepo.save(user);
        assertNotNull(savedUser);
    }

    @Test
    void testRetrieveUserSuccess() {
        Optional<User> userOptional = userRepo.findByEmail("hntrnn12@gmail.com");
        userOptional.ifPresent(user -> System.out.println(Arrays.toString(user.getAvatar())));
    }
}