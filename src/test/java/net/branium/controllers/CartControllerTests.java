package net.branium.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.branium.dtos.course.CourseResponse;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.services.CartService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ActiveProfiles({"test"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest({CartController.class})
class CartControllerTests {

    @MockBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private String courseId;
    private List<CourseResponse> courseResponseList;

    @BeforeEach
    void setUp() {
        courseId = "1";
        courseResponseList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            courseResponseList.add(CourseResponse.builder()
                    .id(i)
                    .image("null")
                    .title("hello cac ban")
                    .price(BigDecimal.valueOf(785897))
                    .discountPrice(BigDecimal.valueOf(785897))
                    .build());
        }
    }

    @Test
    @DisplayName(value = "add course to cart success")
    @Tag(value = "add-course-to-cart")
    @Order(value = 1)
    void testAddCourseToCartSuccess() throws Exception {
        String path = "/carts/items/" + Integer.valueOf(courseId);

        when(cartService.addCourseToUserCart(Integer.valueOf(courseId)))
                .thenReturn(courseResponseList);

        ResultActions resultActions = mockMvc.perform(post(path));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(1000)))
                .andExpect(jsonPath("$.message", is("add course to user's cart success")))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "add course to cart failed because course's id is null")
    @Tag(value = "add-course-to-cart")
    @Order(value = 2)
    void testAddCourseToCartFailedBecauseCourseIdIsNull() throws Exception {
        courseId = "null";
        String path = "/carts/items/" + courseId;

        ResultActions resultActions = mockMvc.perform(post(path));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_PARAM.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_PARAM.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "add course to cart failed because course's id is not int number")
    @Tag(value = "add-course-to-cart")
    @Order(value = 3)
    void testAddCourseToCartFailedBecauseCourseIdIsNotIntNumber() throws Exception {
        courseId = "abcd";
        String path = "/carts/items/" + courseId;

        ResultActions resultActions = mockMvc.perform(post(path));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.BAD_REQUEST.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.BAD_REQUEST.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "add course to cart failed because course is not existed")
    @Tag(value = "add-course-to-cart")
    @Order(value = 4)
    void testAddCourseToCartFailedBecauseCourseIsNotExisted() throws Exception {
        courseId = "100";
        String path = "/carts/items/" + courseId;

        when(cartService.addCourseToUserCart(Integer.valueOf(courseId)))
                .thenThrow(new ApplicationException(ErrorCode.COURSE_NON_EXISTED));

        ResultActions resultActions = mockMvc.perform(post(path));

        resultActions
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.COURSE_NON_EXISTED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.COURSE_NON_EXISTED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "add course to cart failed because course is already bought by user")
    @Tag(value = "add-course-to-cart")
    @Order(value = 5)
    void testAddCourseToCartFailedBecauseCourseIsAlreadyBoughtByUser() throws Exception {
        courseId = "1";
        String path = "/carts/items/" + courseId;

        when(cartService.addCourseToUserCart(Integer.parseInt(courseId)))
                .thenThrow(new ApplicationException(ErrorCode.COURSE_ALREADY_BOUGHT));

        ResultActions resultActions = mockMvc.perform(post(path));

        resultActions
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.COURSE_ALREADY_BOUGHT.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.COURSE_ALREADY_BOUGHT.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "add course to cart failed because course is already in cart")
    @Tag(value = "add-course-to-cart")
    @Order(value = 6)
    void testAddCourseToCartFailedBecauseCourseIsAlreadyInCart() throws Exception {
        courseId = "1";
        String path = "/carts/items/" + courseId;

        when(cartService.addCourseToUserCart(Integer.parseInt(courseId)))
                .thenThrow(new ApplicationException(ErrorCode.COURSE_ALREADY_IN_CART));

        ResultActions resultActions = mockMvc.perform(post(path));

        resultActions
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.COURSE_ALREADY_IN_CART.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.COURSE_ALREADY_IN_CART.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "add course to cart failed because user is not existed")
    @Tag(value = "add-course-to-cart")
    @Order(value = 7)
    void testAddCourseToCartFailedBecauseUserIsNotExisted() throws Exception {
        courseId = "1";
        String path = "/carts/items/" + courseId;

        when(cartService.addCourseToUserCart(Integer.parseInt(courseId)))
                .thenThrow(new ApplicationException(ErrorCode.USER_NON_EXISTED));

        ResultActions resultActions = mockMvc.perform(post(path));

        resultActions
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_NON_EXISTED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_NON_EXISTED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "add course to cart failed because user is not activated")
    @Tag(value = "add-course-to-cart")
    @Order(value = 8)
    void testAddCourseToCartFailedBecauseUserIsNotActivated() throws Exception {
        courseId = "1";
        String path = "/carts/items/" + courseId;

        when(cartService.addCourseToUserCart(Integer.parseInt(courseId)))
                .thenThrow(new ApplicationException(ErrorCode.USER_NOT_ACTIVATED));

        ResultActions resultActions = mockMvc.perform(post(path));

        resultActions
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.USER_NOT_ACTIVATED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.USER_NOT_ACTIVATED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "add course to cart failed because user is not authenticated")
    @Tag(value = "add-course-to-cart")
    @Order(value = 9)
    void testAddCourseToCartFailedBecauseUserIsNotAuthenticated() throws Exception {
        courseId = "1";
        String path = "/carts/items/" + courseId;

        when(cartService.addCourseToUserCart(Integer.parseInt(courseId)))
                .thenThrow(new ApplicationException(ErrorCode.UNAUTHENTICATED));

        ResultActions resultActions = mockMvc.perform(post(path));

        resultActions
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.UNAUTHENTICATED.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.UNAUTHENTICATED.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "add course to cart failed because request method is not supported")
    @Tag(value = "add-course-to-cart")
    @Order(value = 10)
    void testAddCourseToCartFailedBecauseRequestMethodNotSupported() throws Exception {
        courseId = "1";
        String path = "/carts/items/" + courseId;

        when(cartService.addCourseToUserCart(Integer.parseInt(courseId)))
                .thenThrow(new ApplicationException(ErrorCode.REQUEST_METHOD_NOT_SUPPORT));

        ResultActions resultActions = mockMvc.perform(put(path));

        resultActions
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.REQUEST_METHOD_NOT_SUPPORT.getMessage())))
                .andDo(print());
    }
}
