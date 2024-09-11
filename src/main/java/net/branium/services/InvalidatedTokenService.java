package net.branium.services;

import net.branium.domains.InvalidatedToken;

public interface InvalidatedTokenService {
    InvalidatedToken create(InvalidatedToken invalidatedToken);
    void delete();
}
