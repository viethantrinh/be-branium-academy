package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.repositories.CartRepository;
import net.branium.services.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepo;

    @Override
    public long getCartQuantities(String id) {
        long quantity = cartRepo.countTotalCartItems(id);
        return quantity;
    }

    @Override
    public boolean isCartItemExisted(int courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isExisted = cartRepo.existsByUserIdAndCartItemsCourseId(authentication.getName(), courseId) > 0;
        return isExisted;
    }
}
