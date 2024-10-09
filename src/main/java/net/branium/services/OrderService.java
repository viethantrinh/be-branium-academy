package net.branium.services;

import net.branium.domains.Order;
import net.branium.domains.OrderStatus;
import net.branium.dtos.payment.OrderItemRequest;
import net.branium.dtos.payment.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse checkOut(List<OrderItemRequest> request);
}
