package net.branium.services;

import net.branium.domains.User;

import java.util.function.Function;

public interface IJWTService {

    String generateToken(User user);
    boolean verifyToken(String token);
}
