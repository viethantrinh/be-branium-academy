package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.InvalidatedToken;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.repositories.InvalidatedTokenRepository;
import net.branium.services.IInvalidatedTokenService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
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

    @Override
//    @Scheduled(fixedRate = 3600000)
    @Scheduled(fixedRate = 60 * 1000)
    public void delete() {
        List<InvalidatedToken> deletedInvalidatedTokens = invalidatedTokenRepo.deleteByExpirationTimeBefore(new Date());
        log.info("Deleting checking...... -- {}", LocalDateTime.now());
        deletedInvalidatedTokens
                .forEach((token) ->
                        log.info("Deleted token: {}", (token.getJwtid() + " - " + token.getExpirationTime()))
                );
    }

}
