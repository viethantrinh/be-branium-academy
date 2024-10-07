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

    @Query(value = """
            SELECT 
                (COUNT(*) > 0)
            FROM    
                `USER` u
                INNER JOIN `ORDER` o ON u.id = o.user_id
                INNER JOIN `ORDER_DETAIL` oi ON o.id = oi.order_id
            WHERE
                u.id = ?1
                AND
                o.order_status = ?2
                AND 
                oi.course_id = ?3
            """,
            nativeQuery = true)
    long isUserPaid(String userId, String orderStatus, int courseId);
}
