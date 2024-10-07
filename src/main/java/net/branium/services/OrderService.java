package net.branium.services;

import net.branium.domains.Order;
import net.branium.domains.OrderStatus;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrdersByUserIdAndOrderStatus(String userId, OrderStatus orderStatus);
}
