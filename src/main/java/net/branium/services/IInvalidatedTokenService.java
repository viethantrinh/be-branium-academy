package net.branium.services;

import net.branium.domains.InvalidatedToken;

public interface IInvalidatedTokenService {
    InvalidatedToken getById(String id);
    InvalidatedToken create(InvalidatedToken invalidatedToken);
    boolean isExistedById(String id);
}
