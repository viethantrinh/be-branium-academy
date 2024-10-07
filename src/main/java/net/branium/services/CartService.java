package net.branium.services;

public interface CartService {
    long getCartQuantities(String id);
    boolean isCartItemExisted(int courseId);
}
