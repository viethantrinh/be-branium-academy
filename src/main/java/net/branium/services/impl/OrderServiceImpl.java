package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.domains.Order;
import net.branium.domains.OrderStatus;
import net.branium.services.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Override
    public List<Order> getAllOrdersByUserIdAndOrderStatus(String userId, OrderStatus orderStatus) {
        return List.of();
    }
}
