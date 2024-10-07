package net.branium.bootstraps;

import lombok.RequiredArgsConstructor;
import net.branium.domains.Cart;
import net.branium.domains.User;
import net.branium.domains.WishList;
import net.branium.repositories.UserRepository;
import org.checkerframework.checker.units.qual.C;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepo;

    @Override
    public void run(String... args) {
        createCartAndWishListForAllUsers();
    }

    private void createCartAndWishListForAllUsers() {
        List<User> users = userRepo.findAll();
        for (User user : users) {
            if (user.getCart() == null || user.getWishList() == null) {
                Cart newCart = new Cart();
                newCart.setId(user.getId());
                newCart.setUser(user);
                user.setCart(newCart);

                WishList newWishList = new WishList();
                newWishList.setId(user.getId());
                newWishList.setUser(user);
                user.setWishList(newWishList);
            }
        }
        userRepo.saveAll(users);
    }
}
