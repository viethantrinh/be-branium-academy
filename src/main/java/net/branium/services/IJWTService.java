package net.branium.services;

import net.branium.domains.User;
import net.branium.dtos.auth.IntrospectRequest;
import net.branium.dtos.auth.IntrospectResponse;

public interface IJWTService {
    String generateToken(User user);
    IntrospectResponse introspectToken(IntrospectRequest request);
}
