package net.branium.services.impl;

import com.google.gson.Gson;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCancelParams;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import net.branium.domains.*;
import net.branium.dtos.course.CourseResponse;
import net.branium.dtos.payment.OrderDetailResponse;
import net.branium.dtos.payment.OrderItemRequest;
import net.branium.dtos.payment.OrderResponse;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.mappers.CourseMapper;
import net.branium.repositories.*;
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
    private final CartRepository cartRepo;
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
                .totalDiscountPrice(calculateTotalDiscountPrice(request))
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
                .totalDiscountPrice(savedOrder.getTotalDiscountPrice())
                .build();

        return response;
    }

    @Override
    public Map<String, String> createPayment(int orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED));

        PaymentIntentCreateParams paymentIntentCreateParams = PaymentIntentCreateParams.builder()
                .setAmount(order.getTotalDiscountPrice().longValue())
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

    @Override
    public OrderDetailResponse updateOrderStatus(int orderId, String status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED));
        String stripePaymentStatus;

        try {
            stripePaymentStatus = PaymentIntent.retrieve(order.getStripePaymentIntentId()).getStatus();
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

        if (status.equalsIgnoreCase("succeeded")
                && stripePaymentStatus.equalsIgnoreCase("succeeded")) {
            order.setOrderStatus(OrderStatus.SUCCEEDED);
            // Delete all courses which succeeded transaction
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<Integer> courseIds = order.getOrderDetails().stream().map(orderDetail -> orderDetail.getCourse().getId()).toList();
            String cartId = authentication.getName();
            for (Integer courseId : courseIds) {
                cartRepo.deleteByCartIdAndCourseId(cartId, courseId);
            }
        } else if (status.equalsIgnoreCase("failed") || status.equalsIgnoreCase("canceled")) {
            order.setOrderStatus(OrderStatus.FAILED);
            try {
                PaymentIntent resource = PaymentIntent.retrieve(order.getStripePaymentIntentId());
                PaymentIntentCancelParams params = PaymentIntentCancelParams.builder().build();
                resource.cancel(params);
            } catch (StripeException e) {
                throw new ApplicationException(ErrorCode.UNCATEGORIZED_ERROR);
            }
        }

        Order savedOrder = orderRepo.save(order);
        OrderDetailResponse response = convertToOrderDetailResponse(savedOrder);
        return response;
    }

    private OrderDetailResponse convertToOrderDetailResponse(Order savedOrder) {
        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
        orderDetailResponse.setOrderId(savedOrder.getId());
        orderDetailResponse.setOrderStatus(savedOrder.getOrderStatus());
        orderDetailResponse.setTotalPrice(savedOrder.getTotalPrice());
        orderDetailResponse.setOrderDetails(convertToOrderDetails(savedOrder.getOrderDetails()));
        orderDetailResponse.setCreatedAt(savedOrder.getCreatedAt());
        orderDetailResponse.setUpdatedAt(savedOrder.getUpdatedAt());
        return orderDetailResponse;
    }

    private BigDecimal calculateTotalPrice(List<OrderItemRequest> items) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItemRequest i : items) {
            totalPrice = totalPrice.add(i.getPrice());
        }

        return totalPrice;
    }

    private BigDecimal calculateTotalDiscountPrice(List<OrderItemRequest> items) {
        BigDecimal totalDiscountPrice = BigDecimal.ZERO;
        for (OrderItemRequest i : items) {
            totalDiscountPrice = totalDiscountPrice.add(i.getDiscountPrice());
        }

        return totalDiscountPrice;
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
}
