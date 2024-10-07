package net.branium.repositories;

import net.branium.domains.Cart;
import net.branium.domains.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    @Query("SELECT COUNT(ci) FROM CartItem ci WHERE ci.cart.user.id = ?1")
    long countTotalCartItems(String userId);

    @Query("""
            select (count(c) > 0) from Cart c inner join c.cartItems cartItems
            where c.user.id = ?1 and cartItems.course.id = ?2""")
    boolean isCourseExistedInUserCart(String userId, int courseId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.user.id = ?1")
    List<CartItem> findCartItemsByUserId(String userId);

    @Transactional
    @Modifying
    @Query("delete from CartItem ci where ci.cart.id = ?1 and ci.course.id = ?2")
    void deleteByCartIdAndCourseId(String cartId, int courseId);
}
