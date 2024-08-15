package net.branium.services;

import net.branium.domains.InvalidatedToken;

public interface IInvalidatedService {
    InvalidatedToken getById(String id);
    InvalidatedToken create(InvalidatedToken invalidatedToken);
}
