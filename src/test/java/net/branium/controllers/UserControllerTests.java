package net.branium.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.branium.constants.RoleEnum;
import net.branium.domains.Role;
import net.branium.dtos.role.RoleRequest;
import net.branium.dtos.role.RoleResponse;
import net.branium.dtos.user.UserCreateRequest;
import net.branium.dtos.user.UserResponse;
import net.branium.dtos.user.UserUpdateRequest;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.services.UserService;
import net.branium.utils.RandomGenerateUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@ActiveProfiles({"test"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    private final static String baseApiPath = "/users";

    @BeforeEach
    void setUp() {


    }

    @DisplayName("Test api create user success - 201")
    @Order(1)
    @Test
    void givenUserCreateRequest_whenCreateUser_thenReturn201Created() throws Exception {
        RoleRequest roleRequestAdmin = RoleRequest.builder()
                .name(RoleEnum.ROLE_STUDENT.getName())
                .description(RoleEnum.ROLE_STUDENT.getDescription())
                .build();

        Set<RoleRequest> roleRequests = new HashSet<>();
        roleRequests.add(roleRequestAdmin);

        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .email("hntrnn195@gmail.com")
                .password("Sohappy212@")
                .firstName("Hoan")
                .lastName("Pham")
                .enabled(true)
                .gender(true)
                .birthDate(LocalDate.of(2003, Month.MAY, 9))
                .vipLevel(0)
                .phoneNumber("0768701056")
                .roles(roleRequests)
                .build();

        UserResponse userResponse = UserResponse.builder()
                .id(UUID.randomUUID().toString())
                .email(userCreateRequest.getEmail())
                .firstName(userCreateRequest.getFirstName())
                .lastName(userCreateRequest.getLastName())
                .enabled(userCreateRequest.isEnabled())
                .gender(userCreateRequest.isGender())
                .birthDate(userCreateRequest.getBirthDate())
                .avatar(null)
                .vipLevel(userCreateRequest.getVipLevel())
                .phoneNumber(userCreateRequest.getPhoneNumber())
                .roles(userCreateRequest.getRoles().stream().map((
                        (roleRequest) -> RoleResponse.builder()
                                .name(roleRequest.getName())
                                .description(roleRequest.getDescription())
                                .build()
                )).collect(Collectors.toSet()))
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();

        BDDMockito
                .given(userService.createUser(userCreateRequest))
                .willReturn(userResponse);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post(baseApiPath)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userCreateRequest)));

        response
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code", CoreMatchers.is(1000)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message", CoreMatchers.is("create user success")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.id", CoreMatchers.is(userResponse.getId())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.email", CoreMatchers.is(userResponse.getEmail())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.firstName", CoreMatchers.is(userResponse.getFirstName())))
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("Test api create user failed because the request body is invalid - 400")
    @Order(2)
    @Test
    void givenUserCreateRequestWithInvalidField_whenCreateUser_thenReturn400BadRequest() throws Exception {
        UserCreateRequest userCreateRequest = UserCreateRequest
                .builder()
                .email(null)
                .build();


        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post(baseApiPath)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userCreateRequest)));

        response
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code", CoreMatchers.is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message", CoreMatchers.is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("Test api get user by id success - 200")
    @Order(3)
    @Test
    void givenExistedId_whenGetUserById_thenReturn200Ok() throws Exception {
        String id = UUID.randomUUID().toString();
        UserResponse userResponse = UserResponse
                .builder()
                .id(id)
                .email("hntrn12@gmail.com")
                .firstName("Viet Han")
                .lastName("Trinh")
                .enabled(true)
                .gender(true)
                .birthDate(LocalDate.of(2003, Month.MAY, 9))
                .avatar(null)
                .vipLevel(9999)
                .phoneNumber("0768701056")
                .roles(Set.of(
                        RoleResponse.builder()
                                .name(RoleEnum.ROLE_ADMIN.getName())
                                .description(RoleEnum.ROLE_ADMIN.getDescription())
                                .build()
                ))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now().plusHours(4))
                .build();

        BDDMockito
                .given(userService.getUserById(id))
                .willReturn(userResponse);

        String uri = baseApiPath + "/" + id;
        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));


        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code", CoreMatchers.is(1000)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message", CoreMatchers.is("get user by id success")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.id", CoreMatchers.is(userResponse.getId())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.email", CoreMatchers.is(userResponse.getEmail())))
                .andDo(MockMvcResultHandlers.print());

    }

    @DisplayName("Test api get user by id failed because user's id is not existed - 404")
    @Order(4)
    @Test
    void givenNonExistedId_whenGetUserById_thenReturn404NotFound() throws Exception {
        String nonExistedId = UUID.randomUUID().toString();

        BDDMockito
                .given(userService.getUserById(Mockito.anyString()))
                .willThrow(new ApplicationException(ErrorCode.USER_NON_EXISTED));

        String uri = baseApiPath + "/" + nonExistedId;
        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code", CoreMatchers.is(ErrorCode.USER_NON_EXISTED.getCode())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message", CoreMatchers.is(ErrorCode.USER_NON_EXISTED.getMessage())))
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("Test api get user by id failed because user's id is not valid UUID - 400")
    @Order(5)
    @Test
    void givenInvalidID_whenGetUserById_thenReturn400BadRequest() throws Exception {
        String invalidID = RandomGenerateUtils.randomAlphanumericString(64);

        String uri = baseApiPath + "/" + invalidID;
        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code", CoreMatchers.is(ErrorCode.INVALID_PARAM.getCode())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message", CoreMatchers.is(ErrorCode.INVALID_PARAM.getMessage())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.errors[0]", CoreMatchers.is("ID must be an UUID string")))
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("Test api get all users success - 200")
    @Order(6)
    @Test
    void givenListOfUserResponse_whenGetAllUsers_thenReturn200Ok() throws Exception {
        UserResponse userResponse1 = UserResponse
                .builder()
                .id(UUID.randomUUID().toString())
                .email("hntrn12@gmail.com")
                .firstName("Viet Han")
                .lastName("Trinh")
                .enabled(true)
                .gender(true)
                .birthDate(LocalDate.of(2003, Month.MAY, 9))
                .avatar(null)
                .vipLevel(9999)
                .phoneNumber("0768701056")
                .roles(Set.of(
                        RoleResponse.builder()
                                .name(RoleEnum.ROLE_ADMIN.getName())
                                .description(RoleEnum.ROLE_ADMIN.getDescription())
                                .build()
                ))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now().plusHours(4))
                .build();

        UserResponse userResponse2 = UserResponse
                .builder()
                .id(UUID.randomUUID().toString())
                .email("hntrn12@gmail.com")
                .firstName("Viet Han")
                .lastName("Trinh")
                .enabled(true)
                .gender(true)
                .birthDate(LocalDate.of(2003, Month.MAY, 9))
                .avatar(null)
                .vipLevel(9999)
                .phoneNumber("0768701056")
                .roles(Set.of(
                        RoleResponse.builder()
                                .name(RoleEnum.ROLE_ADMIN.getName())
                                .description(RoleEnum.ROLE_ADMIN.getDescription())
                                .build()
                ))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now().plusHours(4))
                .build();

        UserResponse userResponse3 = UserResponse
                .builder()
                .id(UUID.randomUUID().toString())
                .email("hntrn12@gmail.com")
                .firstName("Viet Han")
                .lastName("Trinh")
                .enabled(true)
                .gender(true)
                .birthDate(LocalDate.of(2003, Month.MAY, 9))
                .avatar(null)
                .vipLevel(9999)
                .phoneNumber("0768701056")
                .roles(Set.of(
                        RoleResponse.builder()
                                .name(RoleEnum.ROLE_ADMIN.getName())
                                .description(RoleEnum.ROLE_ADMIN.getDescription())
                                .build()
                ))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now().plusHours(4))
                .build();

        BDDMockito
                .given(userService.getAllUsers())
                .willReturn(List.of(userResponse1, userResponse2, userResponse3));

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.get(baseApiPath)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code", CoreMatchers.is(1000)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message", CoreMatchers.is("get all users success")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result[0].id", CoreMatchers.is(userResponse1.getId())))
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("Test api get all users but users is empty - 204")
    @Order(7)
    @Test
    void givenEmptyList_whenGetAllUsers_thenReturn204NoContent() throws Exception {

        BDDMockito
                .given(userService.getAllUsers())
                .willReturn(Collections.emptyList());

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.get(baseApiPath)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("Test api update user by id success - 200")
    @Order(8)
    @Test
    void givenUserUpdateRequestWithExistedId_whenUpdateUser_thenReturn200Ok() throws Exception {
        String id = UUID.randomUUID().toString();

        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
                .email("hntrnn195@gmail.com")
                .password("Sohappy212@")
                .firstName("Hoan")
                .lastName("Pham")
                .enabled(true)
                .gender(true)
                .birthDate(LocalDate.of(2003, Month.MAY, 9))
                .vipLevel(0)
                .phoneNumber("0768701056")
                .roles(Set.of(
                        RoleRequest.builder()
                                .name(RoleEnum.ROLE_ADMIN.getName())
                                .description(RoleEnum.ROLE_ADMIN.getDescription())
                                .build()
                ))
                .build();

        UserResponse userResponse = UserResponse.builder()
                .id(id)
                .email(userUpdateRequest.getEmail())
                .firstName(userUpdateRequest.getFirstName())
                .lastName(userUpdateRequest.getLastName())
                .enabled(userUpdateRequest.isEnabled())
                .gender(userUpdateRequest.isGender())
                .birthDate(userUpdateRequest.getBirthDate())
                .avatar(null)
                .vipLevel(userUpdateRequest.getVipLevel())
                .phoneNumber(userUpdateRequest.getPhoneNumber())
                .roles(userUpdateRequest.getRoles().stream().map((
                        (roleRequest) -> RoleResponse.builder()
                                .name(roleRequest.getName())
                                .description(roleRequest.getDescription())
                                .build()
                )).collect(Collectors.toSet()))
                .createdAt(null)
                .updatedAt(LocalDateTime.now())
                .build();


        BDDMockito
                .given(userService.updateUser(id, userUpdateRequest))
                .willReturn(userResponse);

        String uri = baseApiPath + "/" + id;
        String requestBody = objectMapper.writeValueAsString(userUpdateRequest);

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code", CoreMatchers.is(1000)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message", CoreMatchers.is("update user success")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.id", CoreMatchers.is(userResponse.getId())))
                .andDo(MockMvcResultHandlers.print());

    }

    @DisplayName("Test api update user by id failed because user's id is invalid - 400")
    @Order(9)
    @Test
    void givenUserUpdateRequestWithInvalidId_whenUpdateUser_thenReturn400BadRequest() throws Exception {
        String id = RandomGenerateUtils.randomAlphanumericString(64);

        BDDMockito
                .given(userService.updateUser(Mockito.eq(id), Mockito.any(UserUpdateRequest.class)))
                .willThrow(new ApplicationException(ErrorCode.INVALID_PARAM));

        String uri = baseApiPath + "/" + id;
        String requestBody = objectMapper.writeValueAsString(UserUpdateRequest.builder().build());

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code", CoreMatchers.is(ErrorCode.INVALID_PARAM.getCode())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message", CoreMatchers.is(ErrorCode.INVALID_PARAM.getMessage())))
                .andDo(MockMvcResultHandlers.print());

    }

    @DisplayName("Test api update user by id failed because user's id is not existed - 404")
    @Order(10)
    @Test
    void givenUserUpdateRequestWithNonExistedId_whenUpdateUser_thenReturn404NotFound() throws Exception {
        String id = RandomGenerateUtils.randomAlphanumericString(64);

        BDDMockito
                .given(userService.updateUser(Mockito.eq(id), Mockito.any(UserUpdateRequest.class)))
                .willThrow(new ApplicationException(ErrorCode.USER_NON_EXISTED));

        String uri = baseApiPath + "/" + id;
        String requestBody = objectMapper.writeValueAsString(UserUpdateRequest.builder().build());

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code", CoreMatchers.is(ErrorCode.USER_NON_EXISTED.getCode())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message", CoreMatchers.is(ErrorCode.USER_NON_EXISTED.getMessage())))
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("Test api delete user by id success - 204")
    @Order(11)
    @Test
    void givenExistedId_whenDeleteUserById_thenReturn204NoContent() throws Exception {
        String id = UUID.randomUUID().toString();

        BDDMockito
                .willDoNothing()
                .given(userService)
                .deleteUserById(id);

        String uri = baseApiPath + "/" + id;
        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.delete(uri));


        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code", CoreMatchers.is(1000)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message", CoreMatchers.is("delete user by id successful")))
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("Test api delete user by id failed because user's id is invalid - 400")
    @Order(12)
    @Test
    void givenInvalidId_whenDeleteUserById_thenReturn204NoContent() throws Exception {
        String id = RandomGenerateUtils.randomAlphanumericString(64);

        BDDMockito
                .willThrow(new ApplicationException(ErrorCode.INVALID_PARAM))
                .given(userService)
                .deleteUserById(Mockito.eq(id));

        String uri = baseApiPath + "/" + id;
        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.delete(uri));


        resultActions
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code", CoreMatchers.is(ErrorCode.INVALID_PARAM.getCode())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message", CoreMatchers.is(ErrorCode.INVALID_PARAM.getMessage())))
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("Test api delete user by id failed because user's id is not existed - 404")
    @Order(13)
    @Test
    void givenNonExistedId_whenDeleteUserById_thenReturn404NotFound() throws Exception {
        String id = RandomGenerateUtils.randomAlphanumericString(64);

        BDDMockito
                .willThrow(new ApplicationException(ErrorCode.USER_NON_EXISTED))
                .given(userService)
                .deleteUserById(Mockito.eq(id));

        String uri = baseApiPath + "/" + id;
        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.delete(uri));


        resultActions
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code", CoreMatchers.is(ErrorCode.USER_NON_EXISTED.getCode())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message", CoreMatchers.is(ErrorCode.USER_NON_EXISTED.getMessage())))
                .andDo(MockMvcResultHandlers.print());
    }
}
