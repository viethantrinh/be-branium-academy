package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.domains.InvalidatedToken;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.repositories.InvalidatedTokenRepository;
import net.branium.services.IInvalidatedTokenService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvalidatedTokenServiceImpl implements IInvalidatedTokenService {
    private final InvalidatedTokenRepository invalidatedTokenRepo;

    @Override
    public InvalidatedToken getById(String id) {
        InvalidatedToken invalidatedToken = invalidatedTokenRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(Error.UNAUTHENTICATED));
        return invalidatedToken;
    }

    @Override
    public InvalidatedToken create(InvalidatedToken invalidatedToken) {
        InvalidatedToken savedToken = invalidatedTokenRepo.save(invalidatedToken);
        return savedToken;
    }

    @Override
    public boolean isExistedById(String id) {
        return invalidatedTokenRepo.existsById(id);
    }
}
