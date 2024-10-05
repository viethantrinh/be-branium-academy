package net.branium.repositories;

import net.branium.domains.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    @Query("SELECT COUNT(ci) FROM CartItem ci WHERE ci.cart.user.id = ?1")
    long countTotalCartItems(String userId);
}
