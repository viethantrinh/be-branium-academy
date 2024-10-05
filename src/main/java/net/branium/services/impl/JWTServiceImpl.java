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
import net.branium.services.JWTService;
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
public class JWTServiceImpl implements JWTService {

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
        String subject = user.getId();
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
     * Verify the token
     *
     * @param token the string token
     * @param refresh flag for verify refresh token
     * @return true if the token is valid, otherwise return false
     */
    @Override
    public boolean verifyToken(String token, boolean refresh) {
        boolean verified;
        boolean expired;
        boolean existed;

        try {
            // use JWSVerifier object to verify the token
            JWSVerifier jwsVerifier = new MACVerifier(secretKey.getBytes());

            // parse the string form of token to SignedJWT object
            SignedJWT signedJWT = SignedJWT.parse(token);

            // verify the token using the JWSVerifier object
            verified = signedJWT.verify(jwsVerifier);

            // get the expiration time of token base on the refresh flag
            Date issueTime = signedJWT.getJWTClaimsSet().getIssueTime();
            Date expirationTime = refresh
                    ? new Date(issueTime.toInstant().plus(validRefreshTokenTime, ChronoUnit.SECONDS).toEpochMilli())
                    : signedJWT.getJWTClaimsSet().getExpirationTime();

            // check if the token is expired
            expired = expirationTime.before(new Date());

            // check if the jwtid of token is existed in database
            String jwtid = signedJWT.getJWTClaimsSet().getJWTID();
            existed = invalidatedTokenRepo.existsById(jwtid);
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }

        return verified && !expired && !existed;
    }

    @Override
    public String refreshToken(String token) {
        if (!verifyToken(token, true)) {
            throw new ApplicationException(ErrorCode.UNAUTHENTICATED);
        }

        SignedJWT signedJWT;
        String refreshToken;

        try {
            signedJWT = SignedJWT.parse(token);
            String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            invalidatedTokenRepo.save(InvalidatedToken.builder()
                    .jwtid(jwtId)
                    .expirationTime(expirationTime)
                    .build());
            String userId = signedJWT.getJWTClaimsSet().getSubject();
            User user = userRepo.findById(userId).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));
            refreshToken = generateToken(user);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }

        return refreshToken;
    }

}
