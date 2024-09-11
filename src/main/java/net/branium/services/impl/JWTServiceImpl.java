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
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.repositories.InvalidatedTokenRepository;
import net.branium.repositories.UserRepository;
import net.branium.services.IJWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements IJWTService {

    private final InvalidatedTokenRepository invalidatedTokenRepo;
    private final UserRepository userRepo;
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.valid-access-token-time}")
    private long validAccessTokenTime;

    @Value("${jwt.valid-refresh-token-time}")
    private long validRefreshTokenTime;

    /**
     * Generate token base on user information
     *
     * @param user user which signed in
     * @return token which is signed
     */
    @Override
    public String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("Branium Academy")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(validAccessTokenTime, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", extractUserAuthority(user))
                .build();
        Payload payload = new Payload(claims.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
        } catch (JOSEException e) {
            throw new RuntimeException(e.getMessage());
        }
        return jwsObject.serialize();
    }

    /**
     * Extract the user's authorities (roles)
     *
     * @param user user which signed in
     * @return authorities list split by one space " "
     */
    private String extractUserAuthority(User user) {
        List<String> authorities = new ArrayList<>();

        String roles = authorities.stream()
                .filter((a) -> a.contains("ROLE_"))
                .collect(Collectors.joining(" "));
        String permissions = authorities.stream()
                .filter((a) -> !a.contains("ROLE_"))
                .collect(Collectors.joining(" "));

        return roles;
    }

    /**
     * Validate the token base on signature, expiration time
     *
     * @param token the token want to verify
     * @return true if token is valid, false if token is invalid
     */
    @Override
    public boolean verifyToken(String token, boolean isRefresh) {
        boolean verified = false;
        boolean expired = false;
        boolean invalidatedTokenExisted = false;
        String jwtid = null;

        try {
            JWSVerifier jwsVerifier = new MACVerifier(secretKey.getBytes());

            SignedJWT signedJWTToken = SignedJWT.parse(token);
            verified = signedJWTToken.verify(jwsVerifier); // check if the signature valid

            Date expirationTime = (isRefresh)
                    ? new Date(signedJWTToken.getJWTClaimsSet().getIssueTime()
                    .toInstant().plus(validRefreshTokenTime, ChronoUnit.SECONDS).toEpochMilli())
                    : signedJWTToken.getJWTClaimsSet().getExpirationTime();
            expired = expirationTime.before(new Date()); // check if the date is valid

            jwtid = signedJWTToken.getJWTClaimsSet().getJWTID();
        } catch (JOSEException | ParseException e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(Error.UNAUTHENTICATED);
        }

        if (jwtid != null) {
            invalidatedTokenExisted = invalidatedTokenRepo.existsById(jwtid);
        }


        return verified && !expired && !invalidatedTokenExisted;
    }

    @Override
    public String refreshToken(String token) {
        if (!verifyToken(token, true)) {
            throw new ApplicationException(Error.UNAUTHENTICATED);
        }

        SignedJWT signedJWT = null;
        try {
            signedJWT = SignedJWT.parse(token);
            String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .jwtid(jwtId)
                    .expirationTime(expirationTime)
                    .build();
            invalidatedTokenRepo.save(invalidatedToken);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }

        String refreshToken = null;
        try {
            String userEmail = signedJWT.getJWTClaimsSet().getSubject();
            User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new ApplicationException(Error.UNAUTHENTICATED));
            refreshToken = generateToken(user);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }

        return refreshToken;
    }

}
