package net.branium.services.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.Permission;
import net.branium.domains.Role;
import net.branium.domains.User;
import net.branium.services.IJWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Override
    public String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("Branium Academy")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.MINUTES).toEpochMilli()))
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

    private String extractUserAuthority(User user) {
        List<String> authorities = new ArrayList<>();

        for (Role role : user.getRoles()) {
            authorities.add("ROLE_" + role.getName());
            for (Permission permission : role.getPermissions()) {
                authorities.add(permission.getName());
            }
        }

        String roles = authorities.stream()
                .filter((a) -> a.contains("ROLE_"))
                .collect(Collectors.joining(" "));
        String permissions = authorities.stream()
                .filter((a) -> !a.contains("ROLE_"))
                .collect(Collectors.joining(" "));

        return roles + " " + permissions;
    }
}
