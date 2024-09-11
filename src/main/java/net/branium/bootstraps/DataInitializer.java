package net.branium.bootstraps;

import lombok.RequiredArgsConstructor;
import net.branium.constants.AuthorityConstants;
import net.branium.constants.RoleEnum;
import net.branium.domains.Role;
import net.branium.domains.User;
import net.branium.repositories.RoleRepository;
import net.branium.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (roleRepo.count() == 0L) {
            initialRole(roleRepo);
        }

        if (userRepo.count() == 0L) {
            initialUser(userRepo, roleRepo, passwordEncoder);
        }
    }



    private void initialRole(RoleRepository roleRepo) {
        Role admin = Role.builder()
                .name(RoleEnum.ROLE_ADMIN.getName())
                .description("admin role")
                .build();

        Role instructor = Role.builder()
                .name(RoleEnum.ROLE_INSTRUCTOR.getName())
                .description("instructor role")
                .build();

        Role student = Role.builder()
                .name(RoleEnum.ROLE_STUDENT.getName())
                .description("student role")
                .build();

        Role customer = Role.builder()
                .name(RoleEnum.ROLE_CUSTOMER.getName())
                .description("customer role")
                .build();

        roleRepo.saveAll(List.of(admin, customer, instructor, student));
    }


    private void initialUser(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) throws IOException {
        Set<Role> roles = new HashSet<>(roleRepo.findAll());

        InputStream inputStream = getClass().getResourceAsStream("/static/images/viethantrinh.jpg");
        byte[] byteArrayAvatar = null;
        if (inputStream != null) {
            byteArrayAvatar = inputStream.readAllBytes();
            inputStream.close();
        }

        User user1 = User
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
                .roles(roles.stream().filter((role) ->
                        role.getName().equals(RoleEnum.ROLE_ADMIN.getName())).collect(Collectors.toSet()))
                .build();

        User user2 = User
                .builder()
                .username("maianhdo")
                .email("hntrnn195@gmail.com")
                .password(passwordEncoder.encode("Sohappy212@"))
                .firstName("Mai Anh")
                .lastName("Do")
                .enabled(true)
                .gender(false)
                .birthDate(LocalDate.of(2001, Month.OCTOBER, 6))
                .avatar(byteArrayAvatar)
                .vipLevel(0)
                .phoneNumber("0978936103")
                .roles(roles.stream().filter((role) ->
                        role.getName().equals(RoleEnum.ROLE_CUSTOMER.getName())).collect(Collectors.toSet()))
                .build();

        userRepo.saveAll(List.of(user1, user2));
    }


}
