package net.branium.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.branium.dtos.course.CourseResponse;
import net.branium.dtos.payment.OrderItemRequest;
import net.branium.dtos.payment.OrderResponse;
import net.branium.dtos.payment.PaymentIntentRequest;
import net.branium.dtos.payment.PaymentIntentResponse;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.services.CourseService;
import net.branium.services.OrderService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles({"test"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest({CourseController.class})
class CourseControllerTests {
    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private List<CourseResponse> courseResponses;
    private CollectionModel<CourseResponse> collectionModel;

    @BeforeEach
    void setUp() {
        courseResponses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CourseResponse courseResponse = CourseResponse.builder()
                    .id(i)
                    .title("java")
                    .image("null")
                    .price(BigDecimal.valueOf(1_000_000))
                    .discountPrice(BigDecimal.valueOf(900_000))
                    .build();
            courseResponses.add(courseResponse);
        }
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(5, 1, 45);
        collectionModel = PagedModel.of(courseResponses, pageMetadata);
    }

    @Test
    @DisplayName(value = "search course by keyword success")
    @Tag(value = "search-course-by-keyword")
    @Order(value = 1)
    void searchCourseByKeywordSuccess() throws Exception {
        String path = "/courses/search";

        when(courseService.getAllCourses(
                anyInt(),
                anyInt(),
                anyString(),
                anyMap()
        ))
                .thenReturn(collectionModel);

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "j");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(1000)))
                .andExpect(jsonPath("$.message", is("search courses success")))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "search course by keyword failed because keyword is null")
    @Tag(value = "search-course-by-keyword")
    @Order(value = 2)
    void searchCourseByKeywordFailedBecauseKeywordIsNull() throws Exception {
        String path = "/courses/search";

        MultiValueMap<String, String> queryParams = new HttpHeaders();

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.BAD_REQUEST.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.BAD_REQUEST.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "search course by keyword failed because keyword contains special character")
    @Tag(value = "search-course-by-keyword")
    @Order(value = 3)
    void searchCourseByKeywordFailedBecauseKeywordIsContainsSpecialCharacter() throws Exception {
        String path = "/courses/search";

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java@@");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_PARAM.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_PARAM.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "search course by keyword failed because page number is not a number")
    @Tag(value = "search-course-by-keyword")
    @Order(value = 4)
    void searchCourseByKeywordFailedBecausePageNumberIsNotANumber() throws Exception {
        String path = "/courses/search";

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("page", "not_number");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_PARAM.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_PARAM.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "search course by keyword failed because page size is not a number")
    @Tag(value = "search-course-by-keyword")
    @Order(value = 5)
    void searchCourseByKeywordFailedBecausePageSizeIsNotANumber() throws Exception {
        String path = "/courses/search";

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("size", "not_number");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_PARAM.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_PARAM.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "search course by keyword failed because page number is less than 1")
    @Tag(value = "search-course-by-keyword")
    @Order(value = 6)
    void searchCourseByKeywordFailedBecausePageNumberIsLessThan1() throws Exception {
        String path = "/courses/search";

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("page", "0");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_PARAM.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_PARAM.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "search course by keyword failed because page size is less than 5")
    @Tag(value = "search-course-by-keyword")
    @Order(value = 7)
    void searchCourseByKeywordFailedBecausePageSizeIsLessThan5() throws Exception {
        String path = "/courses/search";

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("size", "1");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_PARAM.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_PARAM.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "search course by keyword failed because page size is grater than 10")
    @Tag(value = "search-course-by-keyword")
    @Order(value = 8)
    void searchCourseByKeywordFailedBecausePageSizeIsGreaterThan10() throws Exception {
        String path = "/courses/search";

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("size", "11");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_PARAM.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_PARAM.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "search course by keyword failed because user not existed")
    @Tag(value = "search-course-by-keyword")
    @Order(value = 9)
    void searchCourseByKeywordFailedBecauseUserNotExisted() throws Exception {
        String path = "/courses/search";

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("page", "2");
        queryParams.add("size", "10");

        when(courseService.getAllCourses(
                anyInt(),
                anyInt(),
                anyString(),
                anyMap()
        ))
                .thenThrow(new ApplicationException(ErrorCode.USER_NON_EXISTED));

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_NON_EXISTED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_NON_EXISTED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "search course by keyword failed because user not activated")
    @Tag(value = "search-course-by-keyword")
    @Order(value = 10)
    void searchCourseByKeywordFailedBecauseUserNotActivated() throws Exception {
        String path = "/courses/search";

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("page", "2");
        queryParams.add("size", "10");

        when(courseService.getAllCourses(
                anyInt(),
                anyInt(),
                anyString(),
                anyMap()
        ))
                .thenThrow(new ApplicationException(ErrorCode.USER_NOT_ACTIVATED));

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_NOT_ACTIVATED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_NOT_ACTIVATED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "search course by keyword failed because user not authenticated")
    @Tag(value = "search-course-by-keyword")
    @Order(value = 11)
    void searchCourseByKeywordFailedBecauseUserNotAuthenticated() throws Exception {
        String path = "/courses/search";

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("page", "2");
        queryParams.add("size", "10");

        when(courseService.getAllCourses(
                anyInt(),
                anyInt(),
                anyString(),
                anyMap()
        ))
                .thenThrow(new ApplicationException(ErrorCode.UNAUTHENTICATED));

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.UNAUTHENTICATED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.UNAUTHENTICATED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "search course by keyword failed because request method not supported")
    @Tag(value = "search-course-by-keyword")
    @Order(value = 12)
    void searchCourseByKeywordFailedBecauseRequestMethodNotSupported() throws Exception {
        String path = "/courses/search";

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("page", "2");
        queryParams.add("size", "10");


        ResultActions resultActions = mockMvc.perform(post(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sort course success")
    @Tag(value = "sort-course")
    @Order(value = 1)
    void sortCourseSuccess() throws Exception {
        String path = "/courses/search";

        when(courseService.getAllCourses(
                anyInt(),
                anyInt(),
                anyString(),
                anyMap()
        ))
                .thenReturn(collectionModel);

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("sort", "priceAsc");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(1000)))
                .andExpect(jsonPath("$.message", is("search courses success")))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sort course failed because invalid value")
    @Tag(value = "sort-course")
    @Order(value = 2)
    void sortCourseFailedBecauseInvalidValue() throws Exception {
        String path = "/courses/search";

        when(courseService.getAllCourses(
                anyInt(),
                anyInt(),
                anyString(),
                anyMap()
        ))
                .thenReturn(collectionModel);

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("sort", "invalidValue");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_PARAM.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_PARAM.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sort course failed because user not existed")
    @Tag(value = "sort-course")
    @Order(value = 3)
    void sortCourseFailedBecauseUserNotExisted() throws Exception {
        String path = "/courses/search";

        when(courseService.getAllCourses(
                anyInt(),
                anyInt(),
                anyString(),
                anyMap()
        ))
                .thenThrow(new ApplicationException(ErrorCode.USER_NON_EXISTED));

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("sort", "priceDesc");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_NON_EXISTED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_NON_EXISTED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sort course failed because user not activated")
    @Tag(value = "sort-course")
    @Order(value = 4)
    void sortCourseFailedBecauseUserNotActivated() throws Exception {
        String path = "/courses/search";

        when(courseService.getAllCourses(
                anyInt(),
                anyInt(),
                anyString(),
                anyMap()
        ))
                .thenThrow(new ApplicationException(ErrorCode.USER_NOT_ACTIVATED));

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("sort", "priceDesc");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_NOT_ACTIVATED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_NOT_ACTIVATED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sort course failed because user not authenticated")
    @Tag(value = "sort-course")
    @Order(value = 5)
    void sortCourseFailedBecauseUserNotAuthenticated() throws Exception {
        String path = "/courses/search";

        when(courseService.getAllCourses(
                anyInt(),
                anyInt(),
                anyString(),
                anyMap()
        ))
                .thenThrow(new ApplicationException(ErrorCode.UNAUTHENTICATED));

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("sort", "priceDesc");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.UNAUTHENTICATED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.UNAUTHENTICATED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "sort course failed because request method not supported")
    @Tag(value = "sort-course")
    @Order(value = 6)
    void sortCourseFailedBecauseRequestMethodNotSupported() throws Exception {
        String path = "/courses/search";

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("sort", "priceDesc");

        ResultActions resultActions = mockMvc.perform(post(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "filter course by category success")
    @Tag(value = "filter-course-by-category")
    @Order(value = 1)
    void filterCourseSuccess() throws Exception {
        String path = "/courses/search";

        when(courseService.getAllCourses(
                anyInt(),
                anyInt(),
                anyString(),
                anyMap()
        ))
                .thenReturn(collectionModel);

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "ja");
        queryParams.add("sort", "priceAsc");
        queryParams.add("category", "java");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(1000)))
                .andExpect(jsonPath("$.message", is("search courses success")))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "filter course by category failed because invalid category")
    @Tag(value = "filter-course-by-category")
    @Order(value = 2)
    void filterCourseFailedBecauseInvalidCategory() throws Exception {
        String path = "/courses/search";

        when(courseService.getAllCourses(
                anyInt(),
                anyInt(),
                anyString(),
                anyMap()
        ))
                .thenReturn(collectionModel);

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "ja");
        queryParams.add("sort", "priceAsc");
        queryParams.add("category", "nahnah");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_PARAM.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_PARAM.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "filter course failed failed because user not existed")
    @Tag(value = "filter-course-by-category")
    @Order(value = 3)
    void filterCourseFailedFailedBecauseUserNotExisted() throws Exception {
        String path = "/courses/search";

        when(courseService.getAllCourses(
                anyInt(),
                anyInt(),
                anyString(),
                anyMap()
        ))
                .thenThrow(new ApplicationException(ErrorCode.USER_NON_EXISTED));

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("sort", "priceDesc");
        queryParams.add("category", "c++");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_NON_EXISTED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_NON_EXISTED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "filter course failed because user not activated")
    @Tag(value = "filter-course-by-category")
    @Order(value = 4)
    void filterCourseFailedBecauseUserNotActivated() throws Exception {
        String path = "/courses/search";

        when(courseService.getAllCourses(
                anyInt(),
                anyInt(),
                anyString(),
                anyMap()
        ))
                .thenThrow(new ApplicationException(ErrorCode.USER_NOT_ACTIVATED));

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("sort", "priceDesc");
        queryParams.add("category", "c++");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_NOT_ACTIVATED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_NOT_ACTIVATED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "filter course failed because user not authenticated")
    @Tag(value = "filter-course-by-category")
    @Order(value = 5)
    void filterCourseFailedBecauseUserNotAuthenticated() throws Exception {
        String path = "/courses/search";

        when(courseService.getAllCourses(
                anyInt(),
                anyInt(),
                anyString(),
                anyMap()
        ))
                .thenThrow(new ApplicationException(ErrorCode.UNAUTHENTICATED));

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("sort", "priceDesc");
        queryParams.add("category", "c++");

        ResultActions resultActions = mockMvc.perform(get(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.UNAUTHENTICATED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.UNAUTHENTICATED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "filter course failed because request method not supported")
    @Tag(value = "filter-course-by-category")
    @Order(value = 6)
    void filterCourseFailedBecauseRequestMethodNotSupported() throws Exception {
        String path = "/courses/search";

        MultiValueMap<String, String> queryParams = new HttpHeaders();
        queryParams.add("keyword", "Java");
        queryParams.add("sort", "priceDesc");
        queryParams.add("category", "c++");

        ResultActions resultActions = mockMvc.perform(post(path)
                .queryParams(queryParams));

        resultActions
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getMessage())))
                .andDo(print());
    }
}
