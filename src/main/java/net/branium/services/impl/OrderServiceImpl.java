package net.branium.services.impl;

import com.google.gson.Gson;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import net.branium.domains.*;
import net.branium.dtos.course.CourseResponse;
import net.branium.dtos.payment.OrderItemRequest;
import net.branium.dtos.payment.OrderResponse;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.mappers.CourseMapper;
import net.branium.repositories.CourseRepository;
import net.branium.repositories.OrderRepository;
import net.branium.repositories.UserRepository;
import net.branium.repositories.WishListRepository;
import net.branium.services.OrderService;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private final CourseRepository courseRepo;
    private final WishListRepository wishListRepo;
    private final CourseMapper courseMapper;
    private final Environment env;

    @Override
    public OrderResponse checkOut(List<OrderItemRequest> request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        // find user which checkout
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));

        // create new order
        Order order = Order.builder()
                .user(user)
                .orderStatus(OrderStatus.PROCESSING)
                .totalPrice(calculateTotalPrice(request))
                .build();

        // create all order details base on request
        Set<OrderDetail> orderDetails = new HashSet<>();
        for (OrderItemRequest orderItem : request) {

            // check if it is in wish list
            if (wishListRepo.isCourseExistedInUserWishList(user.getId(), orderItem.getCourseId())) {
                throw new ApplicationException(ErrorCode.UNCATEGORIZED_ERROR);
            }

            // check if user already buy this course
            if (orderRepo.isUserPaid(user.getId(), OrderStatus.SUCCEEDED, orderItem.getCourseId())) {
                throw new ApplicationException(ErrorCode.UNCATEGORIZED_ERROR);
            }

            OrderDetail orderDetail = OrderDetail.builder()
                    .course(Course.builder().id(orderItem.getCourseId()).build())
                    .order(order)
                    .build();
            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);

        // save order to db
        Order savedOrder = orderRepo.save(order);

        OrderResponse response = OrderResponse.builder()
                .orderId(savedOrder.getId())
                .orderDetails(convertToOrderDetails(savedOrder.getOrderDetails()))
                .totalPrice(savedOrder.getTotalPrice())
                .build();

        return response;
    }

    private BigDecimal calculateTotalPrice(List<OrderItemRequest> items) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItemRequest i : items) {
            totalPrice = totalPrice.add(i.getDiscountPrice());
        }

        return totalPrice;
    }

    private List<CourseResponse> convertToOrderDetails(Set<OrderDetail> orderDetails) {
        List<CourseResponse> courseResponses = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            Course course = courseRepo.findById(orderDetail.getCourse().getId())
                    .orElseThrow(() -> new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED));
            CourseResponse courseResponse = courseMapper.toCourseResponse(course);
            courseResponses.add(courseResponse);
        }
        return courseResponses;
    }

    @Override
    public Map<String, String> createPayment(int orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED));

        PaymentIntentCreateParams paymentIntentCreateParams = PaymentIntentCreateParams.builder()
                .setAmount(order.getTotalPrice().longValue())
                .setCurrency("vnd")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .build()
                )
                .build();

        PaymentIntent paymentIntent;

        try {
            paymentIntent = PaymentIntent.create(paymentIntentCreateParams);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

        order.setStripePaymentIntentId(paymentIntent.getId());
        orderRepo.save(order);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("clientSecret", paymentIntent.getClientSecret());
        responseData.put("publishableKey", env.getProperty("stripe.publishable-key"));

        return responseData;
    }
}
