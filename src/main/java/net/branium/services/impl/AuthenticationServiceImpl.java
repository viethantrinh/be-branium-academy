package net.branium.services.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.constants.ApplicationConstants;
import net.branium.domains.User;
import net.branium.dtos.auth.AuthenticationRequest;
import net.branium.dtos.auth.AuthenticationResponse;
import net.branium.dtos.auth.IntrospectRequest;
import net.branium.dtos.auth.IntrospectResponse;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.repositories.UserRepository;
import net.branium.services.IAuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String userEmail = request.getEmail();
        String password = request.getPassword();
        User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new ApplicationException(Error.USER_NON_EXISTED));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApplicationException(Error.UNAUTHENTICATED);
        }
        String token = generateToken(userEmail);
        return AuthenticationResponse.builder().accessToken(token).authenticated(true).build();
    }

    @Override
    public String generateToken(String email) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .issuer("Branium Academy")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(3, ChronoUnit.MINUTES).toEpochMilli()))
                .claim("scope", "custom")
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

    @Override
    public IntrospectResponse introspectToken(IntrospectRequest request) {
        String token = request.getAccessToken();
        boolean valid;
        try {
            JWSVerifier jwsVerifier = new MACVerifier(secretKey.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            boolean verified = signedJWT.verify(jwsVerifier);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean expired = Instant.now().isAfter(expirationTime.toInstant());
            valid = verified && !expired;
        } catch (JOSEException | ParseException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        return IntrospectResponse.builder().valid(valid).build();
    }
}
