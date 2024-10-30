package net.branium.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import net.branium.dtos.auth.SignInRequest;
import net.branium.dtos.auth.SignUpRequest;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.exceptions.GlobalExceptionHandler;
import net.branium.services.AuthenticationService;
import net.branium.services.JWTService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles({"test"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest({AuthenticationController.class})
class AuthenticationControllerTests {

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private HttpServletRequest httpServletRequest;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private SignUpRequest signUpRequest;
    private SignInRequest signInRequest;

    @BeforeEach
    void setUp() {
        signUpRequest = SignUpRequest.builder()
                .firstName("Han")
                .lastName("Trinh")
                .email("hntrn12@gmail.com")
                .password("Sohappy212@")
                .build();

        signInRequest = SignInRequest.builder()
                .email("hntrnn12@gmail.com")
                .password("Sohappy212@")
                .build();
    }

    @Test
    @DisplayName(value = "sign up success")
    @Tag(value = "sign-up")
    @Order(1)
    void testSignUpSuccess() throws Exception {
        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(1000)))
                .andExpect(jsonPath("$.message", is("sign up succeeded")))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because first name is null")
    @Tag(value = "sign-up")
    @Order(2)
    void testSignUpFailedBecauseFirstNameIsNull() throws Exception {
        signUpRequest.setFirstName(null);

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because first name have less than 2 characters")
    @Tag(value = "sign-up")
    @Order(3)
    void testSignUpFailedBecauseFirstNameHaveLessThan2Characters() throws Exception {
        signUpRequest.setFirstName("a");

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because first name have more than 51 characters")
    @Tag(value = "sign-up")
    @Order(4)
    void testSignUpFailedBecauseFirstNameHaveMoreThan50Characters() throws Exception {
        signUpRequest.setFirstName(RandomStringUtils.randomAlphanumeric(51));

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because first name have number")
    @Tag(value = "sign-up")
    @Order(5)
    void testSignUpFailedBecauseFirstNameHaveNumber() throws Exception {
        signUpRequest.setFirstName("4556");

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because first name have special characters")
    @Tag(value = "sign-up")
    @Order(6)
    void testSignUpFailedBecauseFirstNameHaveSpecialCharacter() throws Exception {
        signUpRequest.setFirstName("Han@@@");

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because last name is null")
    @Tag(value = "sign-up")
    @Order(7)
    void testSignUpFailedBecauseLastNameIsNull() throws Exception {
        signUpRequest.setLastName(null);

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because last name have less than 2 characters")
    @Tag(value = "sign-up")
    @Order(8)
    void testSignUpFailedBecauseLastNameHaveLessThan2Characters() throws Exception {
        signUpRequest.setLastName("a");

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because last name have more than 51 characters")
    @Tag(value = "sign-up")
    @Order(9)
    void testSignUpFailedBecauseLastNameHaveMoreThan50Characters() throws Exception {
        signUpRequest.setLastName(RandomStringUtils.randomAlphanumeric(51));

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because last name have number")
    @Tag(value = "sign-up")
    @Order(10)
    void testSignUpFailedBecauseLastNameHaveNumber() throws Exception {
        signUpRequest.setLastName("4556");

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because last name have special characters")
    @Tag(value = "sign-up")
    @Order(11)
    void testSignUpFailedBecauseLastNameHaveSpecialCharacter() throws Exception {
        signUpRequest.setLastName("Han@@@");

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because email is null")
    @Tag(value = "sign-up")
    @Order(12)
    void testSignUpFailedBecauseEmailIsNull() throws Exception {
        signUpRequest.setEmail(null);

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because email have less than 5 characters")
    @Tag(value = "sign-up")
    @Order(13)
    void testSignUpFailedBecauseEmailHaveLessThan5Characters() throws Exception {
        signUpRequest.setEmail("a");

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because email have more than 128 characters")
    @Tag(value = "sign-up")
    @Order(14)
    void testSignUpFailedBecauseEmailHaveMoreThan128Characters() throws Exception {
        signUpRequest.setEmail(RandomStringUtils.randomAlphanumeric(130));

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because email is invalid")
    @Tag(value = "sign-up")
    @Order(15)
    void testSignUpFailedBecauseEmailIsInvalid() throws Exception {
        signUpRequest.setEmail("hntrnn12.com");

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because email is existed in system")
    @Tag(value = "sign-up")
    @Order(16)
    void testSignUpFailedBecauseEmailIsExistedInSystem() throws Exception {

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doThrow(new ApplicationException(ErrorCode.USER_EXISTED))
                .when(authenticationService)
                .signUp(any(SignUpRequest.class), any(HttpServletRequest.class));

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_EXISTED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_EXISTED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because password is null")
    @Tag(value = "sign-up")
    @Order(17)
    void testSignUpFailedBecausePasswordIsNull() throws Exception {
        signUpRequest.setPassword(null);

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because password is less than 8 characters")
    @Tag(value = "sign-up")
    @Order(18)
    void testSignUpFailedBecausePasswordIsLessThan8Characters() throws Exception {
        signUpRequest.setPassword("1234");

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because password is not contain at least 1 number")
    @Tag(value = "sign-up")
    @Order(19)
    void testSignUpFailedBecausePasswordNotContainAtLeast1Number() throws Exception {
        signUpRequest.setPassword("hantrinhviet@F");

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because password is not contain at least 1 special character")
    @Tag(value = "sign-up")
    @Order(20)
    void testSignUpFailedBecausePasswordNotContainAtLeast1SpecialCharacter() throws Exception {
        signUpRequest.setPassword("hantrinhviet1F");

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign up failed because password is not contain at least 1 uppercase character")
    @Tag(value = "sign-up")
    @Order(21)
    void testSignUpFailedBecausePasswordNotContainAtLeast1UppercaseCharacter() throws Exception {
        signUpRequest.setPassword("hantrinhviet1@@");

        String path = "/auth/sign-up";

        String requestBody = objectMapper.writeValueAsString(signUpRequest);

        doNothing().when(authenticationService).signUp(signUpRequest, httpServletRequest);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign in success")
    @Tag(value = "sign-in")
    @Order(1)
    void testSignInSuccess() throws Exception {
        String path = "/auth/sign-in";

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        when(authenticationService.signIn(signInRequest))
                .thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0YTM5ZTBmZS0yN2EwL" +
                        "TQ3ODktOGJhMi1iN2U0OTY3N2RiMzQiLCJzY29wZSI6IlNUVURFTlQiLCJpc3Mi" +
                        "OiJCcmFuaXVtIEFjYWRlbXkiLCJleHAiOjE3MzAwMDM2MDgsImlhdCI6MTczMDAwMDA" +
                        "wOCwianRpIjoiZWRhYzBkYTEtZTI5NS00NTU0LTliNDItY2EyMGUyZWU4MTQ1In0.4-AU" +
                        "TR3oQqEzcz6EvDrqLxU599Ll8dAoe6PlvMmTODUUntPOujtV5lD-d9egUGKPtJqioOrX5b" +
                        "yFn9i7WR70sA");

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(1000)))
                .andExpect(jsonPath("$.message", is("sign in success")))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign in failed because email is null")
    @Tag(value = "sign-in")
    @Order(2)
    void testSignInFailedBecauseEmailIsNull() throws Exception {
        signInRequest.setEmail(null);

        String path = "/auth/sign-in";

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        when(authenticationService.signIn(signInRequest))
                .thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0YTM5ZTBmZS0yN2EwL" +
                        "TQ3ODktOGJhMi1iN2U0OTY3N2RiMzQiLCJzY29wZSI6IlNUVURFTlQiLCJpc3Mi" +
                        "OiJCcmFuaXVtIEFjYWRlbXkiLCJleHAiOjE3MzAwMDM2MDgsImlhdCI6MTczMDAwMDA" +
                        "wOCwianRpIjoiZWRhYzBkYTEtZTI5NS00NTU0LTliNDItY2EyMGUyZWU4MTQ1In0.4-AU" +
                        "TR3oQqEzcz6EvDrqLxU599Ll8dAoe6PlvMmTODUUntPOujtV5lD-d9egUGKPtJqioOrX5b" +
                        "yFn9i7WR70sA");

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign in failed because email have less than 5 characters")
    @Tag(value = "sign-in")
    @Order(3)
    void testSignInFailedBecauseEmailHaveLessThan5Characters() throws Exception {
        signInRequest.setEmail("a");

        String path = "/auth/sign-in";

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        when(authenticationService.signIn(signInRequest))
                .thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0YTM5ZTBmZS0yN2EwL" +
                        "TQ3ODktOGJhMi1iN2U0OTY3N2RiMzQiLCJzY29wZSI6IlNUVURFTlQiLCJpc3Mi" +
                        "OiJCcmFuaXVtIEFjYWRlbXkiLCJleHAiOjE3MzAwMDM2MDgsImlhdCI6MTczMDAwMDA" +
                        "wOCwianRpIjoiZWRhYzBkYTEtZTI5NS00NTU0LTliNDItY2EyMGUyZWU4MTQ1In0.4-AU" +
                        "TR3oQqEzcz6EvDrqLxU599Ll8dAoe6PlvMmTODUUntPOujtV5lD-d9egUGKPtJqioOrX5b" +
                        "yFn9i7WR70sA");

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign in failed because email have more than 128 characters")
    @Tag(value = "sign-in")
    @Order(4)
    void testSignInFailedBecauseEmailHaveMoreThan128Characters() throws Exception {
        signInRequest.setEmail(RandomStringUtils.randomAlphanumeric(130));

        String path = "/auth/sign-in";

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        when(authenticationService.signIn(signInRequest))
                .thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0YTM5ZTBmZS0yN2EwL" +
                        "TQ3ODktOGJhMi1iN2U0OTY3N2RiMzQiLCJzY29wZSI6IlNUVURFTlQiLCJpc3Mi" +
                        "OiJCcmFuaXVtIEFjYWRlbXkiLCJleHAiOjE3MzAwMDM2MDgsImlhdCI6MTczMDAwMDA" +
                        "wOCwianRpIjoiZWRhYzBkYTEtZTI5NS00NTU0LTliNDItY2EyMGUyZWU4MTQ1In0.4-AU" +
                        "TR3oQqEzcz6EvDrqLxU599Ll8dAoe6PlvMmTODUUntPOujtV5lD-d9egUGKPtJqioOrX5b" +
                        "yFn9i7WR70sA");

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign in failed because email is invalid")
    @Tag(value = "sign-in")
    @Order(5)
    void testSignInFailedBecauseEmailIsInvalid() throws Exception {
        signInRequest.setEmail("hntrnn12.com");

        String path = "/auth/sign-in";

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        when(authenticationService.signIn(signInRequest))
                .thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0YTM5ZTBmZS0yN2EwL" +
                        "TQ3ODktOGJhMi1iN2U0OTY3N2RiMzQiLCJzY29wZSI6IlNUVURFTlQiLCJpc3Mi" +
                        "OiJCcmFuaXVtIEFjYWRlbXkiLCJleHAiOjE3MzAwMDM2MDgsImlhdCI6MTczMDAwMDA" +
                        "wOCwianRpIjoiZWRhYzBkYTEtZTI5NS00NTU0LTliNDItY2EyMGUyZWU4MTQ1In0.4-AU" +
                        "TR3oQqEzcz6EvDrqLxU599Ll8dAoe6PlvMmTODUUntPOujtV5lD-d9egUGKPtJqioOrX5b" +
                        "yFn9i7WR70sA");

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign in failed because password is null")
    @Tag(value = "sign-in")
    @Order(6)
    void testSignInFailedBecausePasswordIsNull() throws Exception {
        signInRequest.setPassword(null);

        String path = "/auth/sign-in";

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        when(authenticationService.signIn(signInRequest))
                .thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0YTM5ZTBmZS0yN2EwL" +
                        "TQ3ODktOGJhMi1iN2U0OTY3N2RiMzQiLCJzY29wZSI6IlNUVURFTlQiLCJpc3Mi" +
                        "OiJCcmFuaXVtIEFjYWRlbXkiLCJleHAiOjE3MzAwMDM2MDgsImlhdCI6MTczMDAwMDA" +
                        "wOCwianRpIjoiZWRhYzBkYTEtZTI5NS00NTU0LTliNDItY2EyMGUyZWU4MTQ1In0.4-AU" +
                        "TR3oQqEzcz6EvDrqLxU599Ll8dAoe6PlvMmTODUUntPOujtV5lD-d9egUGKPtJqioOrX5b" +
                        "yFn9i7WR70sA");

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign in failed because password is less than 8 characters")
    @Tag(value = "sign-in")
    @Order(7)
    void testSignInFailedBecausePasswordIsLessThan8Characters() throws Exception {
        signInRequest.setPassword("1234");

        String path = "/auth/sign-in";

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        when(authenticationService.signIn(signInRequest))
                .thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0YTM5ZTBmZS0yN2EwL" +
                        "TQ3ODktOGJhMi1iN2U0OTY3N2RiMzQiLCJzY29wZSI6IlNUVURFTlQiLCJpc3Mi" +
                        "OiJCcmFuaXVtIEFjYWRlbXkiLCJleHAiOjE3MzAwMDM2MDgsImlhdCI6MTczMDAwMDA" +
                        "wOCwianRpIjoiZWRhYzBkYTEtZTI5NS00NTU0LTliNDItY2EyMGUyZWU4MTQ1In0.4-AU" +
                        "TR3oQqEzcz6EvDrqLxU599Ll8dAoe6PlvMmTODUUntPOujtV5lD-d9egUGKPtJqioOrX5b" +
                        "yFn9i7WR70sA");

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign in failed because password is not contain at least 1 number")
    @Tag(value = "sign-in")
    @Order(8)
    void testSignInFailedBecausePasswordNotContainAtLeast1Number() throws Exception {
        signInRequest.setPassword("hantrinhviet@F");

        String path = "/auth/sign-in";

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        when(authenticationService.signIn(signInRequest))
                .thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0YTM5ZTBmZS0yN2EwL" +
                        "TQ3ODktOGJhMi1iN2U0OTY3N2RiMzQiLCJzY29wZSI6IlNUVURFTlQiLCJpc3Mi" +
                        "OiJCcmFuaXVtIEFjYWRlbXkiLCJleHAiOjE3MzAwMDM2MDgsImlhdCI6MTczMDAwMDA" +
                        "wOCwianRpIjoiZWRhYzBkYTEtZTI5NS00NTU0LTliNDItY2EyMGUyZWU4MTQ1In0.4-AU" +
                        "TR3oQqEzcz6EvDrqLxU599Ll8dAoe6PlvMmTODUUntPOujtV5lD-d9egUGKPtJqioOrX5b" +
                        "yFn9i7WR70sA");

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign in failed because password is not contain at least 1 special character")
    @Tag(value = "sign-in")
    @Order(9)
    void testSignInFailedBecausePasswordNotContainAtLeast1SpecialCharacter() throws Exception {
        signInRequest.setPassword("hantrinhviet1F");

        String path = "/auth/sign-in";

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        when(authenticationService.signIn(signInRequest))
                .thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0YTM5ZTBmZS0yN2EwL" +
                        "TQ3ODktOGJhMi1iN2U0OTY3N2RiMzQiLCJzY29wZSI6IlNUVURFTlQiLCJpc3Mi" +
                        "OiJCcmFuaXVtIEFjYWRlbXkiLCJleHAiOjE3MzAwMDM2MDgsImlhdCI6MTczMDAwMDA" +
                        "wOCwianRpIjoiZWRhYzBkYTEtZTI5NS00NTU0LTliNDItY2EyMGUyZWU4MTQ1In0.4-AU" +
                        "TR3oQqEzcz6EvDrqLxU599Ll8dAoe6PlvMmTODUUntPOujtV5lD-d9egUGKPtJqioOrX5b" +
                        "yFn9i7WR70sA");

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign in failed because password is not contain at least 1 uppercase character")
    @Tag(value = "sign-in")
    @Order(10)
    void testSignInFailedBecausePasswordNotContainAtLeast1UppercaseCharacter() throws Exception {
        signInRequest.setPassword("hantrinhviet1@@");

        String path = "/auth/sign-in";

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        when(authenticationService.signIn(signInRequest))
                .thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0YTM5ZTBmZS0yN2EwL" +
                        "TQ3ODktOGJhMi1iN2U0OTY3N2RiMzQiLCJzY29wZSI6IlNUVURFTlQiLCJpc3Mi" +
                        "OiJCcmFuaXVtIEFjYWRlbXkiLCJleHAiOjE3MzAwMDM2MDgsImlhdCI6MTczMDAwMDA" +
                        "wOCwianRpIjoiZWRhYzBkYTEtZTI5NS00NTU0LTliNDItY2EyMGUyZWU4MTQ1In0.4-AU" +
                        "TR3oQqEzcz6EvDrqLxU599Ll8dAoe6PlvMmTODUUntPOujtV5lD-d9egUGKPtJqioOrX5b" +
                        "yFn9i7WR70sA");

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_FIELD.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_FIELD.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign in failed because user not activated")
    @Tag(value = "sign-in")
    @Order(11)
    void testSignInFailedBecauseUserNotActivated() throws Exception {
        String path = "/auth/sign-in";

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        when(authenticationService.signIn(signInRequest))
                .thenThrow(new ApplicationException(ErrorCode.USER_NOT_ACTIVATED));

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_NOT_ACTIVATED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_NOT_ACTIVATED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign in failed because user not existed")
    @Tag(value = "sign-in")
    @Order(12)
    void testSignInFailedBecauseUserNotExisted() throws Exception {
        String path = "/auth/sign-in";

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        when(authenticationService.signIn(signInRequest))
                .thenThrow(new ApplicationException(ErrorCode.USER_NON_EXISTED));

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_NON_EXISTED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_NON_EXISTED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign in failed because user's credentials is not valid")
    @Tag(value = "sign-in")
    @Order(13)
    void testSignInFailedBecauseUserCredentialsIsNotValid() throws Exception {
        String path = "/auth/sign-in";

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        when(authenticationService.signIn(signInRequest))
                .thenThrow(new ApplicationException(ErrorCode.UNAUTHENTICATED));

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.UNAUTHENTICATED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.UNAUTHENTICATED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sign in failed because request method not support")
    @Tag(value = "sign-in")
    @Order(14)
    void testSignInFailedBecauseRequestMethodNotSupport() throws Exception {
        String path = "/auth/sign-in";

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        when(authenticationService.signIn(signInRequest))
                .thenThrow(new ApplicationException(ErrorCode.UNAUTHENTICATED));

        ResultActions resultActions = mockMvc.perform(get(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getMessage())))
                .andDo(print());
    }


}
