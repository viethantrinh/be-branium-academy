package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.domains.InvalidatedToken;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.repositories.InvalidatedTokenRepository;
import net.branium.services.IInvalidatedService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvalidatedServiceImpl implements IInvalidatedService {
    private final InvalidatedTokenRepository invalidatedTokenRepo;

    @Override
    public InvalidatedToken getById(String id) {
        return invalidatedTokenRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(Error.UNAUTHENTICATED));
    }

    @Override
    public InvalidatedToken create(InvalidatedToken invalidatedToken) {
        InvalidatedToken savedToken = invalidatedTokenRepo.save(invalidatedToken);
        return savedToken;
    }
}
