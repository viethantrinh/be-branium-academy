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
import net.branium.exceptions.ErrorCode;
import net.branium.repositories.InvalidatedTokenRepository;
import net.branium.repositories.UserRepository;
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
public class JWTService implements net.branium.services.JWTService {

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
        String subject = user.getEmail();
        String issuer = "Branium Academy";
        String scope = extractUserRoles(user);

        // build a header of JWT
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // build a payload of JWT
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer(issuer)
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(validAccessTokenTime, ChronoUnit.SECONDS)
                        .toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", scope)
                .build();

        Payload payload = new Payload(claims.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        // sign the JWT with a signer
        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
        } catch (JOSEException e) {
            throw new RuntimeException(e.getMessage());
        }

        // return the string form of token
        return jwsObject.serialize();
    }

    /**
     * Extract the user's authorities (roles)
     *
     * @param user user which signed in
     * @return authorities list split by one space " "
     */
    private String extractUserRoles(User user) {
        StringJoiner sj = new StringJoiner(" ");
        Set<Role> roles = user.getRoles();
        roles.forEach((role) -> sj.add(role.getName()));
        return sj.toString();
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
            throw new ApplicationException(ErrorCode.UNAUTHENTICATED);
        }

        if (jwtid != null) {
            invalidatedTokenExisted = invalidatedTokenRepo.existsById(jwtid);
        }


        return verified && !expired && !invalidatedTokenExisted;
    }

    @Override
    public String refreshToken(String token) {
        if (!verifyToken(token, true)) {
            throw new ApplicationException(ErrorCode.UNAUTHENTICATED);
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
            User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new ApplicationException(ErrorCode.UNAUTHENTICATED));
            refreshToken = generateToken(user);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }

        return refreshToken;
    }

}
