package net.branium.repositories;

import net.branium.domains.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    @Query("SELECT COUNT(ci) FROM CartItem ci WHERE ci.cart.user.id = ?1")
    long countTotalCartItems(String userId);

    @Query(value = """
            SELECT
                 (COUNT(*) > 0)
            FROM
                `user` u
                 INNER JOIN `cart` c ON u.id = c.id
                 INNER JOIN `cart_item` ci ON c.id = ci.cart_id
            WHERE
                u.id = ?1
                AND
                ci.course_id = ?2
            """, nativeQuery = true)
    long existsByUserIdAndCartItemsCourseId(String userId, int courseId);


}
