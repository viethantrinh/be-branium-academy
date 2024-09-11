package net.branium.services;

import net.branium.domains.User;

public interface JWTService {

    String generateToken(User user);

    boolean verifyToken(String token, boolean isRefresh);

    String refreshToken(String token);
}
