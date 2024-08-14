package net.branium.bootstraps;

import lombok.RequiredArgsConstructor;
import net.branium.constants.AuthorityConstants;
import net.branium.domains.Permission;
import net.branium.domains.Role;
import net.branium.domains.User;
import net.branium.repositories.PermissionRepository;
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
    private final PermissionRepository permissionRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (permissionRepo.count() == 0L) {
            initialPermission(permissionRepo);
        }

        if (roleRepo.count() == 0L) {
            initialRole(roleRepo);
        }

        if (userRepo.count() == 0L) {
            initialUser(userRepo, roleRepo, passwordEncoder);
        }
    }

    private void initialPermission(PermissionRepository permissionRepo) {
        Permission createUser = Permission.builder()
                .name(AuthorityConstants.PERMISSION_CREATE_USER)
                .description("Permission to create user in system")
                .build();


        Permission deleteUser = Permission.builder()
                .name(AuthorityConstants.PERMISSION_DELETE_USER)
                .description("Permission to delete user in system")
                .build();

        Permission getUser = Permission.builder()
                .name(AuthorityConstants.PERMISSION_GET_USER_INFORMATION)
                .description("Permission to get user information in system")
                .build();

        Permission getUsers = Permission.builder()
                .name(AuthorityConstants.PERMISSION_GET_USERS_INFORMATION)
                .description("Permission to get all users information in system")
                .build();

        Permission updateUser = Permission.builder()
                .name(AuthorityConstants.PERMISSION_UPDATE_USER)
                .description("Permission to get update user information in system")
                .build();

        permissionRepo.saveAll(List.of(createUser, deleteUser, getUser, getUsers, updateUser));
    }

    private void initialRole(RoleRepository roleRepo) {
        Set<Permission> permissions = new HashSet<>(permissionRepo.findAll());
        Map<String, Permission> permissionMap = new HashMap<>();
        permissions.forEach((p) -> permissionMap.put(p.getName(), p));

        Role admin = Role.builder()
                .name(AuthorityConstants.ROLE_ADMIN)
                .description("admin role")
                .permissions(Set.of(
                        permissionMap.get(AuthorityConstants.PERMISSION_CREATE_USER),
                        permissionMap.get(AuthorityConstants.PERMISSION_GET_USERS_INFORMATION),
                        permissionMap.get(AuthorityConstants.PERMISSION_GET_USER_INFORMATION),
                        permissionMap.get(AuthorityConstants.PERMISSION_UPDATE_USER),
                        permissionMap.get(AuthorityConstants.PERMISSION_DELETE_USER)
                ))
                .build();

        Role instructor = Role.builder()
                .name(AuthorityConstants.ROLE_INSTRUCTOR)
                .description("instructor role")
                .build();

        Role student = Role.builder()
                .name(AuthorityConstants.ROLE_STUDENT)
                .description("student role")
                .build();

        Role customer = Role.builder()
                .name(AuthorityConstants.ROLE_CUSTOMER)
                .description("customer role")
                .permissions(Set.of(
                        permissionMap.get(AuthorityConstants.PERMISSION_CREATE_USER),
                        permissionMap.get(AuthorityConstants.PERMISSION_GET_USERS_INFORMATION),
                        permissionMap.get(AuthorityConstants.PERMISSION_GET_USER_INFORMATION),

                        permissionMap.get(AuthorityConstants.PERMISSION_UPDATE_USER),
                        permissionMap.get(AuthorityConstants.PERMISSION_DELETE_USER)
                ))
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
                        role.getName().equals(AuthorityConstants.ROLE_ADMIN)).collect(Collectors.toSet()))
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
                .vipLevel(1)
                .phoneNumber("0978936103")
                .roles(roles.stream().filter((role) ->
                        role.getName().equals(AuthorityConstants.ROLE_CUSTOMER)).collect(Collectors.toSet()))
                .build();

        userRepo.saveAll(List.of(user1, user2));
    }


}
