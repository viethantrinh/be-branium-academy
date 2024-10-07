package net.branium.repositories;

import net.branium.domains.Order;
import net.branium.domains.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserIdAndOrderStatus(String userId, OrderStatus orderStatus);

    @Query("""
            select (count(o) > 0) 
            from 
            Order o inner join o.orderDetails orderDetails
            where 
                o.user.id = ?1 
                and o.orderStatus = ?2 
                and orderDetails.course.id = ?3""")
    boolean isUserPaid(String userId, OrderStatus orderStatus, int courseId);


}
