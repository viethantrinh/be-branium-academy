package net.branium.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.branium.domains.RoleEnum;
import net.branium.dtos.resource.ResourceResponse;
import net.branium.dtos.role.RoleResponse;
import net.branium.dtos.user.StudentResponse;
import net.branium.dtos.user.StudentUpdateRequest;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.services.UserService;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code",
                        is(1000)))
                .andExpect(jsonPath("$.message",
                        is("get student info successful")))
                .andExpect(jsonPath("$.result.id",
                        is(studentResponse.getId())))
                .andDo(print());
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
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code",
                        is(ErrorCode.USER_NON_EXISTED.getCode())))
                .andExpect(jsonPath("$.message",
                        is(ErrorCode.USER_NON_EXISTED.getMessage())))
                .andDo(print());
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
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(1000)))
                .andExpect(jsonPath("$.message", is("update student info successful")))
                .andExpect(jsonPath("$.result.id", is(studentResponse.getId())))
                .andDo(print());

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
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code",
                        is(ErrorCode.USER_NON_EXISTED.getCode())))
                .andExpect(jsonPath("$.message",
                        is(ErrorCode.USER_NON_EXISTED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "get user's image success")
    @Tag(value = "get-user-image")
    @Order(1)
    void testGetUserImageSuccess() throws Exception {
        ResourceResponse response = ResourceResponse.builder()
                .url("http://localhost:8080/branium-academy/api/v1/resources/image/74df4df3")
                .build();

        String path = "/users/image";

        when(userService.getStudentImage())
                .thenReturn(response);

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(1000)))
                .andExpect(jsonPath("$.message", is("get student's image success")))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "get user's image failed because user not existed")
    @Tag(value = "get-user-image")
    @Order(2)
    void testGetUserImageFailedBecauseUserNotExisted() throws Exception {
        String path = "/users/image";

        when(userService.getStudentImage())
                .thenThrow(new ApplicationException(ErrorCode.USER_NON_EXISTED));

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_NON_EXISTED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_NON_EXISTED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "get user's image failed because user's image not existed")
    @Tag(value = "get-user-image")
    @Order(3)
    void testGetUserImageFailedBecauseUserImageNotExisted() throws Exception {
        String path = "/users/image";

        when(userService.getStudentImage())
                .thenThrow(new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED));

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.RESOURCE_NON_EXISTED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.RESOURCE_NON_EXISTED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "get user's image failed because user not activated")
    @Tag(value = "get-user-image")
    @Order(4)
    void testGetUserImageFailedBecauseUserNotActivated() throws Exception {
        String path = "/users/image";

        when(userService.getStudentImage())
                .thenThrow(new ApplicationException(ErrorCode.USER_NOT_ACTIVATED));

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_NOT_ACTIVATED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_NOT_ACTIVATED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "get user's image failed because user not authenticated")
    @Tag(value = "get-user-image")
    @Order(5)
    void testGetUserImageFailedBecauseUserNotAuthenticated() throws Exception {
        String path = "/users/image";

        when(userService.getStudentImage())
                .thenThrow(new ApplicationException(ErrorCode.UNAUTHENTICATED));

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.UNAUTHENTICATED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.UNAUTHENTICATED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "get user's image failed because request method not supported")
    @Tag(value = "get-user-image")
    @Order(6)
    void testGetUserImageFailedBecauseRequestMethodNotSupported() throws Exception {
        String path = "/users/image";

        when(userService.getStudentImage())
                .thenThrow(new ApplicationException(ErrorCode.REQUEST_METHOD_NOT_SUPPORT));

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "update user's image success")
    @Tag(value = "update-user-image")
    @Order(1)
    void testUpdateUserImageSuccess() throws Exception {
        ResourceResponse response = ResourceResponse.builder()
                .url("http://localhost:8080/branium-academy/api/v1/resources/image/74df4df3")
                .build();

        MockMultipartFile mockFile = new MockMultipartFile(
                "image", // the name of the file parameter
                "image.png", // the original filename
                "image/png", // the content type
                "Mock image content".getBytes() // the content of the file
        );


        String path = "/users/image";

        when(userService.updateStudentImage(any(MultipartFile.class)))
                .thenReturn(response);

        ResultActions resultActions =
                mockMvc.perform(multipart(path).file(mockFile));

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is(1000)))
                .andExpect(jsonPath("$.message", is("upload student's image success")))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "update user's image failed because user not existed")
    @Tag(value = "update-user-image")
    @Order(2)
    void testUpdateUserImageFailedBecauseUserNotExisted() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "image", // the name of the file parameter
                "image.png", // the original filename
                "image/png", // the content type
                "Mock image content".getBytes() // the content of the file
        );

        String path = "/users/image";

        when(userService.updateStudentImage(any(MultipartFile.class)))
                .thenThrow(new ApplicationException(ErrorCode.USER_NON_EXISTED));

        ResultActions resultActions =
                mockMvc.perform(multipart(path).file(mockFile));

        resultActions
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_NON_EXISTED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_NON_EXISTED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "update user's image failed because image is null")
    @Tag(value = "update-user-image")
    @Order(3)
    void testUpdateUserImageFailedBecauseImageIsNull() throws Exception {

        String path = "/users/image";

        ResultActions resultActions =
                mockMvc.perform(multipart(path));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(ErrorCode.BAD_REQUEST.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.BAD_REQUEST.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "update user's image failed because user not activated")
    @Tag(value = "update-user-image")
    @Order(4)
    void testUpdateUserImageFailedBecauseUserNotActivated() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "image", // the name of the file parameter
                "image.png", // the original filename
                "image/png", // the content type
                "Mock image content".getBytes() // the content of the file
        );

        String path = "/users/image";

        when(userService.updateStudentImage(any(MultipartFile.class)))
                .thenThrow(new ApplicationException(ErrorCode.USER_NOT_ACTIVATED));

        ResultActions resultActions =
                mockMvc.perform(multipart(path).file(mockFile));

        resultActions
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_NOT_ACTIVATED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_NOT_ACTIVATED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "update user's image failed because user not authenticated")
    @Tag(value = "update-user-image")
    @Order(5)
    void testUpdateUserImageFailedBecauseUserNotAuthenticated() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "image", // the name of the file parameter
                "image.png", // the original filename
                "image/png", // the content type
                "Mock image content".getBytes() // the content of the file
        );

        String path = "/users/image";

        when(userService.updateStudentImage(any(MultipartFile.class)))
                .thenThrow(new ApplicationException(ErrorCode.UNAUTHENTICATED));

        ResultActions resultActions =
                mockMvc.perform(multipart(path).file(mockFile));

        resultActions
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", is(ErrorCode.UNAUTHENTICATED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.UNAUTHENTICATED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "update user's image failed because request method not supported")
    @Tag(value = "update-user-image")
    @Order(6)
    void testUpdateUserImageFailedBecauseRequestMethodNotSupported() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "image", // the name of the file parameter
                "image.png", // the original filename
                "image/png", // the content type
                "Mock image content".getBytes() // the content of the file
        );

        String path = "/users/image";

        when(userService.updateStudentImage(any(MultipartFile.class)))
                .thenThrow(new ApplicationException(ErrorCode.REQUEST_METHOD_NOT_SUPPORT));

        ResultActions resultActions =
                mockMvc.perform(multipart(path).file(mockFile));

        resultActions
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.code", is(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "update user's image failed because image extension is not supported")
    @Tag(value = "update-user-image")
    @Order(7)
    void testUpdateUserImageFailedBecauseImageExtensionNotSupported() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "image", // the name of the file parameter
                "image.png", // the original filename
                "image/ngungu", // the content type
                "Mock image content".getBytes() // the content of the file
        );

        String path = "/users/image";

        when(userService.updateStudentImage(any(MultipartFile.class)))
                .thenThrow(new ApplicationException(ErrorCode.INVALID_IMAGE_EXTENSION));

        ResultActions resultActions =
                mockMvc.perform(multipart(path).file(mockFile));

        resultActions
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_IMAGE_EXTENSION.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_IMAGE_EXTENSION.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "update user's image failed because image is over sized")
    @Tag(value = "update-user-image")
    @Order(8)
    void testUpdateUserImageFailedBecauseImageIsOverSized() throws Exception {
        byte[] largeFileContent = new byte[5242881];
        Arrays.fill(largeFileContent, (byte) 1);
        MockMultipartFile mockFile = new MockMultipartFile(
                "image", // the name of the file parameter
                "image.png", // the original filename
                "image/png", // the content type
                largeFileContent // the content of the file
        );

        String path = "/users/image";

        when(userService.updateStudentImage(any(MultipartFile.class)))
                .thenThrow(new ApplicationException(ErrorCode.OVERSIZE_FILE));

        ResultActions resultActions =
                mockMvc.perform(multipart(path).file(mockFile));

        resultActions
                .andExpect(status().isPayloadTooLarge())
                .andExpect(jsonPath("$.code", is(ErrorCode.OVERSIZE_FILE.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.OVERSIZE_FILE.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "get user's info success")
    @Tag(value = "get-user-info")
    @Order(1)
    void testGetUserInfoSuccess() throws Exception {
        String path = "/users/info";

        when(userService.getStudentInfo())
                .thenReturn(studentResponse);

        ResultActions resultActions =
                mockMvc.perform(get(path));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(1000)))
                .andExpect(jsonPath("$.message", is("get student info successful")))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "get user's info failed because user not existed")
    @Tag(value = "get-user-info")
    @Order(2)
    void testGetUserInfoFailedBecauseUserNotExisted() throws Exception {
        String path = "/users/info";

        when(userService.getStudentInfo())
                .thenThrow(new ApplicationException(ErrorCode.USER_NON_EXISTED));

        ResultActions resultActions =
                mockMvc.perform(get(path));

        resultActions
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_NON_EXISTED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_NON_EXISTED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "get user's info failed because user not activated")
    @Tag(value = "get-user-info")
    @Order(3)
    void testGetUserInfoFailedBecauseUserNotActivated() throws Exception {
        String path = "/users/info";

        when(userService.getStudentInfo())
                .thenThrow(new ApplicationException(ErrorCode.USER_NOT_ACTIVATED));

        ResultActions resultActions =
                mockMvc.perform(get(path));

        resultActions
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_NOT_ACTIVATED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_NOT_ACTIVATED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "get user's info failed because user not authenticated")
    @Tag(value = "get-user-info")
    @Order(4)
    void testGetUserInfoFailedBecauseUserNotAuthenticated() throws Exception {
        String path = "/users/info";

        when(userService.getStudentInfo())
                .thenThrow(new ApplicationException(ErrorCode.UNAUTHENTICATED));

        ResultActions resultActions =
                mockMvc.perform(get(path));

        resultActions
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", is(ErrorCode.UNAUTHENTICATED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.UNAUTHENTICATED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "get user's info failed because request method not supported")
    @Tag(value = "get-user-info")
    @Order(5)
    void testGetUserInfoFailedBecauseRequestMethodNotSupported() throws Exception {
        String path = "/users/info";

        when(userService.getStudentInfo())
                .thenThrow(new ApplicationException(ErrorCode.REQUEST_METHOD_NOT_SUPPORT));

        ResultActions resultActions =
                mockMvc.perform(get(path));

        resultActions
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.code", is(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getMessage())))
                .andDo(print());
    }
}
