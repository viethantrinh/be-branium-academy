package net.branium.services;

import net.branium.dtos.payment.OrderDetailResponse;
import net.branium.dtos.payment.OrderItemRequest;
import net.branium.dtos.payment.OrderResponse;
import net.branium.dtos.payment.PaymentIntentResponse;

import java.util.List;
import java.util.Map;

public interface OrderService {
    OrderResponse checkOut(List<OrderItemRequest> request);
    PaymentIntentResponse createPayment(int orderId);
    OrderDetailResponse updateOrderStatus(int orderId, String status);
}
