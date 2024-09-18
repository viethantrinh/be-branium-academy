package net.branium.services.impl;

import net.branium.constants.RoleEnum;
import net.branium.domains.Role;
import net.branium.domains.User;
import net.branium.dtos.role.RoleRequest;
import net.branium.dtos.user.UserCreateRequest;
import net.branium.dtos.user.UserResponse;
import net.branium.dtos.user.UserUpdateRequest;
import net.branium.exceptions.ApplicationException;
import net.branium.mappers.UserMapper;
import net.branium.mappers.UserMapperImpl;
import net.branium.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;


@ActiveProfiles({"test"})
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepo;

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserCreateRequest userCreateRequest;
    private UserUpdateRequest userUpdateRequest;

    @BeforeEach
    void setUp() {
        RoleRequest roleRequestAdmin = RoleRequest.builder()
                .name(RoleEnum.ROLE_ADMIN.getName())
                .description(RoleEnum.ROLE_ADMIN.getDescription())
                .build();

        Set<RoleRequest> roleRequests = new HashSet<>();
        roleRequests.add(roleRequestAdmin);

        Role roleAdmin = Role.builder()
                .name(RoleEnum.ROLE_ADMIN.getName())
                .description(RoleEnum.ROLE_ADMIN.getDescription())
                .build();

        Set<Role> roles = new HashSet<>();
        roles.add(roleAdmin);

        user = User.builder()
                .id(UUID.randomUUID().toString())
                .email("hntrnn12@gmail.com")
                .password("Sohappy212@")
                .firstName("Han")
                .lastName("Trinh")
                .enabled(true)
                .gender(true)
                .birthDate(LocalDate.of(2003, Month.DECEMBER, 2))
                .vipLevel(9999)
                .phoneNumber("0768701056")
                .roles(roles)
                .createdAt(LocalDateTime.now())
                .build();

        userCreateRequest = UserCreateRequest.builder()
                .email("hntrnn12@gmail.com")
                .password("Sohappy212@")
                .firstName("Han")
                .lastName("Trinh")
                .enabled(true)
                .gender(true)
                .birthDate(LocalDate.of(2003, Month.DECEMBER, 2))
                .vipLevel(9999)
                .phoneNumber("0768701056")
                .roles(roleRequests)
                .build();


        userUpdateRequest = UserUpdateRequest.builder()
                .email("hntrnn195@gmail.com")
                .password("Sohappy212@")
                .firstName("Mai")
                .lastName("Anh")
                .enabled(true)
                .gender(false)
                .birthDate(LocalDate.of(2003, Month.DECEMBER, 2))
                .vipLevel(9999)
                .phoneNumber("0768701056")
                .roles(roleRequests)
                .build();
    }

    @Order(1)
    @DisplayName("Test create user successful")
    @Test
    void givenUserCreateRequestWithNonExistedEmail_whenSaveUser_thenReturnUserResponseObject() {
        // given - arrange phase
        BDDMockito
                .given(userRepo.existsByEmail(userCreateRequest.getEmail()))
                .willReturn(false);


        BDDMockito.given(userRepo.save(Mockito.any(User.class)))
                .willReturn(user);


        // when
        UserResponse response = userService.createUser(userCreateRequest);

        // then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(user.getId());
    }

    @Order(2)
    @DisplayName("Test create user failed because email is existed")
    @Test
    void givenUserCreateRequestWithExistedEmail_whenSaveUser_thenThrowsException() {
        // given - arrange phase
        UserCreateRequest request = UserCreateRequest.builder()
                .email("hntrnn12@gmail.com")
                .password("Sohappy212@")
                .firstName("Han")
                .lastName("Trinh")
                .enabled(true)
                .gender(true)
                .birthDate(LocalDate.of(2003, Month.DECEMBER, 2))
                .vipLevel(9999)
                .phoneNumber("0768701056")
                .roles(
                        Set.of(
                                RoleRequest.builder()
                                        .name(RoleEnum.ROLE_ADMIN.getName())
                                        .description(RoleEnum.ROLE_ADMIN.getDescription())
                                        .build()
                        ))
                .build();

        BDDMockito
                .given(userRepo.existsByEmail(request.getEmail()))
                .willReturn(true);

        // when
        org.junit.jupiter.api.Assertions.assertThrows(ApplicationException.class, () -> {
            userService.createUser(request);
        });

        // then
        Mockito.verify(userRepo, Mockito.never()).save(Mockito.any(User.class));
    }

    @Order(3)
    @DisplayName("Test get user by id success")
    @Test
    void givenExistedUserId_whenGetUserById_thenReturnUserResponseObject() {
        String id = user.getId();
        BDDMockito
                .given(userRepo.findById(id))
                .willReturn(Optional.of(user));

        UserResponse response = userService.getUserById(id);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(id);
        Assertions.assertThat(response.getEmail()).isEqualTo(user.getEmail());
    }

    @Order(4)
    @DisplayName("Test get user by id failed because user's id not existed")
    @Test
    void givenNonExistedUserId_whenGetUserById_thenThrowsException() {
        String id = user.getId();
        BDDMockito
                .given(userRepo.findById(id))
                .willThrow(ApplicationException.class);

        org.junit.jupiter.api.Assertions.assertThrows(ApplicationException.class, () -> {
            userService.getUserById(id);
        });

        Mockito.verify(userRepo, Mockito.times(1))
                .findById(Mockito.anyString());
    }

    @Order(5)
    @DisplayName("Test get all users success (positive > 0 case)")
    @Test
    void givenUserList_whenGetAllUsers_thenReturnListOfUserResponse() {
        User user1 = User.builder()
                .id(UUID.randomUUID().toString())
                .email("hntrnn195@gmail.com")
                .password("Sohappy212@")
                .firstName("Mai")
                .lastName("Any")
                .enabled(true)
                .gender(true)
                .birthDate(LocalDate.of(2003, Month.DECEMBER, 2))
                .vipLevel(9999)
                .phoneNumber("0768701056")
                .roles(
                        Set.of(
                                Role.builder()
                                        .name(RoleEnum.ROLE_STUDENT.getName())
                                        .description(RoleEnum.ROLE_STUDENT.getDescription())
                                        .build()
                        ))
                .createdAt(LocalDateTime.now())
                .build();

        BDDMockito
                .given(userRepo.findAll())
                .willReturn(List.of(user, user1));

        List<UserResponse> users = userService.getAllUsers();

        Assertions.assertThat(users).isNotEmpty();
        Assertions.assertThat(users).size().isEqualTo(2);
        Assertions.assertThat(users.getFirst().getId()).isEqualTo(user.getId());
    }

    @Order(6)
    @DisplayName("Test get all users success (negative = 0 case)")
    @Test
    void givenEmptyUserList_whenGetAllUsers_thenReturnEmptyListOfUserResponse() {
        BDDMockito
                .given(userRepo.findAll())
                .willReturn(Collections.emptyList());

        List<UserResponse> users = userService.getAllUsers();

        Assertions.assertThat(users).isEmpty();
        Assertions.assertThat(users).size().isEqualTo(0);
    }

    @Order(7)
    @DisplayName("Test update user success")
    @Test
    void givenExistedUserIdAndUserUpdateRequest_whenUpdate_thenReturnUserResponseObject() {
        String id = user.getId();

        BDDMockito
                .given(userRepo.findById(id))
                .willReturn(Optional.of(user));

        BDDMockito
                .given(userRepo.save(Mockito.any(User.class)))
                .willReturn(user);

        UserResponse response = userService.updateUser(id, userUpdateRequest);


        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getEmail()).isEqualTo(userUpdateRequest.getEmail());
        Assertions.assertThat(response.getFirstName()).isEqualTo(userUpdateRequest.getFirstName());
    }

    @Order(8)
    @DisplayName("Test update user failed because user's id not existed")
    @Test
    void givenNonExistedUserIdAndUserUpdateRequest_whenUpdate_thenThrowsException() {
        BDDMockito
                .given(userRepo.findById(Mockito.anyString()))
                .willThrow(ApplicationException.class);


        org.junit.jupiter.api.Assertions.assertThrows(ApplicationException.class, () -> {
            userService.updateUser(Mockito.anyString(), userUpdateRequest);
        });

        Mockito.verify(userRepo, Mockito.never())
                .save(Mockito.any(User.class));
    }

    @Order(9)
    @DisplayName("Test delete user by id success")
    @Test
    void givenExistedUserId_whenDeleteById_thenDeleteUserSuccess() {
        String id = user.getId();

        BDDMockito
                .given(userRepo.existsById(id))
                .willReturn(true);

        BDDMockito
                .willDoNothing()
                .given(userRepo)
                .deleteById(id);

        userService.deleteUserById(id);

        BDDMockito.verify(userRepo, Mockito.times(1)).deleteById(id);
    }

    @Order(10)
    @DisplayName("Test delete user failed because user's id not existed")
    @Test
    void givenNonExistedUserId_whenDeleteById_thenThrowsException() {
        String id = user.getId();

        BDDMockito
                .given(userRepo.existsById(id))
                .willReturn(false);


        org.junit.jupiter.api.Assertions.assertThrows(ApplicationException.class, () -> {
            userService.deleteUserById(id);
        });

        Mockito.verify(userRepo, Mockito.never())
                .deleteById(Mockito.any(String.class));
    }
}
