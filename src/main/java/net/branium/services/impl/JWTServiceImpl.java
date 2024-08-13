package net.branium.services.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.InvalidatedToken;
import net.branium.domains.Role;
import net.branium.domains.User;
import net.branium.dtos.auth.IntrospectRequest;
import net.branium.dtos.auth.IntrospectResponse;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.repositories.InvalidatedTokenRepository;
import net.branium.services.IJWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements IJWTService {
    @Value("${jwt.secret-key}")
    private String secretKey;

    private final InvalidatedTokenRepository invalidatedTokenRepo;

    @Override
    public String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("Branium Academy")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(2, ChronoUnit.MINUTES).toEpochMilli()))
                .claim("scope", getUserScope(user.getRoles()))
                .jwtID(UUID.randomUUID().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey));
            return jwsObject.serialize();
        } catch (JOSEException ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException("Cannot sign token!");
        }
    }

    private String getUserScope(Set<Role> roles) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (roles == null) {
            throw new ApplicationException(Error.ROLE_NON_EXISTED);
        }

        roles.forEach((role) -> {
            stringJoiner.add(role.getName());
        });

        return stringJoiner.toString();
    }

    @Override
    public IntrospectResponse introspectToken(IntrospectRequest request) throws ParseException, JOSEException {
        String token = request.getAccessToken();
        boolean isValid = true;
        try {
            verify(token);
        } catch (ApplicationException e) {
            isValid = false;
        }
        return IntrospectResponse.builder().valid(isValid).build();
    }

    @Override
    public SignedJWT verify(String token) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(secretKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean verified = signedJWT.verify(jwsVerifier);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean expired = expirationTime.before(new Date());

        if (!(verified && !expired)) {
            throw new ApplicationException(Error.UNAUTHENTICATED);
        }

        String jwtid = signedJWT.getJWTClaimsSet().getJWTID();

        if (invalidatedTokenRepo.existsById(jwtid)) {
            throw new ApplicationException(Error.UNAUTHENTICATED);
        }

        return signedJWT;
    }
}
