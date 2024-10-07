package net.branium.repositories;

import net.branium.domains.Order;
import net.branium.domains.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserIdAndOrderStatus(String userId, OrderStatus orderStatus);

}
