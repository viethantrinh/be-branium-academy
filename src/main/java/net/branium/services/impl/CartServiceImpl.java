package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.repositories.CartRepository;
import net.branium.services.CartService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepo;

    @Override
    public long getCartQuantitiesByUserId(String id) {
        long quantity = cartRepo.countTotalCartItems(id);
        return quantity;
    }
}
