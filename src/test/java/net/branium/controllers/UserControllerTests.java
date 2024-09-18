package net.branium.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.branium.constants.RoleEnum;
import net.branium.domains.Role;
import net.branium.dtos.role.RoleRequest;
import net.branium.dtos.role.RoleResponse;
import net.branium.dtos.user.UserCreateRequest;
import net.branium.dtos.user.UserResponse;
import net.branium.exceptions.ErrorCode;
import net.branium.services.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ActiveProfiles({"test"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired private MockMvc mockMvc;
    @MockBean private UserService userService;
    @Autowired private ObjectMapper objectMapper;
    private final static String baseApiPath = "/users";

    @BeforeEach
    void setUp() {


    }

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

}
