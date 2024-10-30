package net.branium.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.branium.domains.RoleEnum;
import net.branium.dtos.role.RoleRequest;
import net.branium.dtos.role.RoleResponse;
import net.branium.dtos.user.*;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.services.UserService;
import net.branium.utils.RandomGenerateUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;
import java.util.stream.Collectors;

@ActiveProfiles({"test"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest({UserController.class})
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private StudentResponse studentResponse;
    private StudentUpdateRequest studentUpdateRequest;

    @BeforeEach
    void setUp() {
        studentResponse = StudentResponse.builder()
                .id(UUID.randomUUID().toString())
                .email("hntrnn12@gmail.com")
                .firstName("Han")
                .lastName("Trinh")
                .gender(true)
                .dateOfBirth(LocalDate.of(2003, Month.DECEMBER, 2))
                .phoneNumber("0768701056")
                .roles(Set.of(RoleResponse.builder().name(RoleEnum.ROLE_ADMIN.getName()).build()))
                .build();

        studentUpdateRequest = StudentUpdateRequest.builder()
                .firstName("Han")
                .lastName("Trinh")
                .gender(true)
                .dateOfBirth(LocalDate.of(2003, Month.DECEMBER, 2))
                .phoneNumber("0768701056")
                .build();
    }

    @Test
    @DisplayName(value = "Test get student's info success")
    @Order(1)
    void givenStudentResponse_whenGetStudent_thenReturn200OK() throws Exception {
        // given
        BDDMockito
                .given(userService.getStudentInfo())
                .willReturn(studentResponse);

        String requestUrl = "/users/info";

        // when
        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.get(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then - assert
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                        CoreMatchers.is(1000)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        CoreMatchers.is("get student info successful")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.id",
                        CoreMatchers.is(studentResponse.getId())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName(value = "Test get student's info failed because student not existed")
    @Order(2)
    void givenNonExistedEmail_whenGetStudent_thenReturn404NotFound() throws Exception {
        // given
        BDDMockito
                .given(userService.getStudentInfo())
                .willThrow(new ApplicationException(ErrorCode.USER_NON_EXISTED));

        String requestUrl = "/users/info";

        // when
        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.get(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then - assert
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                        CoreMatchers.is(ErrorCode.USER_NON_EXISTED.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        CoreMatchers.is(ErrorCode.USER_NON_EXISTED.getMessage())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName(value = "Test update student's info success")
    @Order(3)
    void givenStudentRequest_whenUpdateStudent_thenReturn200OK() throws Exception {
        BDDMockito
                .given(userService.updateStudentInfo(Mockito.any(StudentUpdateRequest.class)))
                .willReturn(studentResponse);

        String requestUrl = "/users/info";

        String requestBody = objectMapper.writeValueAsString(studentUpdateRequest);

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.put(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody));


        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", CoreMatchers.is(1000)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("update student info successful")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.id", CoreMatchers.is(studentResponse.getId())))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName(value = "Test update student's info failed because student not existed")
    @Order(3)
    void givenStudentRequest_whenUpdateStudent_thenReturn404NotFound() throws Exception {
        BDDMockito
                .given(userService.updateStudentInfo(Mockito.any(StudentUpdateRequest.class)))
                .willThrow(new ApplicationException(ErrorCode.USER_NON_EXISTED));

        String requestUrl = "/users/info";

        String requestBody = objectMapper.writeValueAsString(studentUpdateRequest);

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.put(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody));


        resultActions
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                        CoreMatchers.is(ErrorCode.USER_NON_EXISTED.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        CoreMatchers.is(ErrorCode.USER_NON_EXISTED.getMessage())))
                .andDo(MockMvcResultHandlers.print());
    }

}
