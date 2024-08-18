package net.branium.services;

import net.branium.domains.InvalidatedToken;

public interface IInvalidatedTokenService {
    InvalidatedToken create(InvalidatedToken invalidatedToken);
    void delete();
}
