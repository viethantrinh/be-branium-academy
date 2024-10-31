package net.branium.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.branium.dtos.course.CourseResponse;
import net.branium.dtos.payment.OrderItemRequest;
import net.branium.dtos.payment.OrderResponse;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.services.CartService;
import net.branium.services.OrderService;
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
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles({"test"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest({OrderController.class})
class OrderControllerTests {
    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private List<OrderItemRequest> orderItemRequests = new ArrayList<>();
    private OrderResponse orderResponse;

    @BeforeEach
    void setUp() {
        List<CourseResponse> courseResponses = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            OrderItemRequest request = OrderItemRequest.builder()
                    .courseId(i)
                    .price(BigDecimal.valueOf(1_000_000))
                    .discountPrice(BigDecimal.valueOf(900_000))
                    .build();
            orderItemRequests.add(request);

            CourseResponse courseResponse = CourseResponse.builder()
                    .id(request.getCourseId())
                    .title("blah blah")
                    .image("null")
                    .price(request.getPrice())
                    .discountPrice(request.getDiscountPrice())
                    .build();
            courseResponses.add(courseResponse);
        }

        orderResponse = OrderResponse.builder()
                .orderId(1)
                .orderDetails(courseResponses)
                .totalPrice(BigDecimal.valueOf(10_000_000))
                .totalDiscountPrice(BigDecimal.valueOf(10_000_000))
                .build();
    }

    @Test
    @DisplayName(value = "check out success")
    @Tag(value = "check-out")
    @Order(value = 1)
    void testCheckoutSuccess() throws Exception {
        String path = "/orders/checkout";
        String requestBody = objectMapper.writeValueAsString(orderItemRequests);
        when(orderService.checkOut(orderItemRequests))
                .thenReturn(orderResponse);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(1000)))
                .andExpect(jsonPath("$.message", is("create order successful")))
                .andExpect(jsonPath("$.result.orderId", is(orderResponse.getOrderId())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "check out failed because empty list in body")
    @Tag(value = "check-out")
    @Order(value = 2)
    void testCheckoutFailedBecauseEmptyListInBody() throws Exception {
        orderItemRequests = Collections.emptyList();
        String path = "/orders/checkout";
        String requestBody = objectMapper.writeValueAsString(orderItemRequests);

        when(orderService.checkOut(orderItemRequests))
                .thenReturn(orderResponse);

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.INVALID_PARAM.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.INVALID_PARAM.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "check out failed because course in order is already bought by user")
    @Tag(value = "check-out")
    @Order(value = 3)
    void testCheckoutFailedBecauseCourseInOrderIsAlreadyBoughtByUser() throws Exception {
        String path = "/orders/checkout";
        String requestBody = objectMapper.writeValueAsString(orderItemRequests);

        when(orderService.checkOut(orderItemRequests))
                .thenThrow(new ApplicationException(ErrorCode.COURSE_ALREADY_BOUGHT));

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.COURSE_ALREADY_BOUGHT.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.COURSE_ALREADY_BOUGHT.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "check out failed because course in order is in wishlist")
    @Tag(value = "check-out")
    @Order(value = 4)
    void testCheckoutFailedBecauseCourseInOrderIsInWishlist() throws Exception {
        String path = "/orders/checkout";
        String requestBody = objectMapper.writeValueAsString(orderItemRequests);

        when(orderService.checkOut(orderItemRequests))
                .thenThrow(new ApplicationException(ErrorCode.COURSE_ALREADY_IN_WISHLIST));

        ResultActions resultActions = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCode.COURSE_ALREADY_IN_WISHLIST.getCode())))
                .andExpect(jsonPath("$.message", is(ErrorCode.COURSE_ALREADY_IN_WISHLIST.getMessage())))
                .andDo(print());
    }

    @Test
    @DisplayName(value = "check out failed because user is not existed")
    @Tag(value = "check-out")
    @Order(value = 5)
    void testCheckoutFailedBecauseUserNotExisted() throws Exception {
        String path = "/orders/checkout";
        String requestBody = objectMapper.writeValueAsString(orderItemRequests);

        when(orderService.checkOut(orderItemRequests))
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
    @DisplayName(value = "check out failed because user is not activated")
    @Tag(value = "check-out")
    @Order(value = 6)
    void testCheckoutFailedBecauseUserNotActivated() throws Exception {
        String path = "/orders/checkout";
        String requestBody = objectMapper.writeValueAsString(orderItemRequests);

        when(orderService.checkOut(orderItemRequests))
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
    @DisplayName(value = "check out failed because user is not authenticated")
    @Tag(value = "check-out")
    @Order(value = 7)
    void testCheckoutFailedBecauseUserNotAuthenticated() throws Exception {
        String path = "/orders/checkout";
        String requestBody = objectMapper.writeValueAsString(orderItemRequests);

        when(orderService.checkOut(orderItemRequests))
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
    @DisplayName(value = "check out failed because request method not supported")
    @Tag(value = "check-out")
    @Order(value = 8)
    void testCheckoutFailedBecauseRequestMethodNotSupported() throws Exception {
        String path = "/orders/checkout";
        String requestBody = objectMapper.writeValueAsString(orderItemRequests);

//        when(orderService.checkOut(orderItemRequests))
//                .thenThrow(new ApplicationException(ErrorCode.REQUEST_METHOD_NOT_SUPPORT));

        ResultActions resultActions = mockMvc.perform(put(path)
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
