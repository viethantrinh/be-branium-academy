package net.branium.securities.jwt;

import com.nimbusds.jose.JWSAlgorithm;
import lombok.RequiredArgsConstructor;
import net.branium.services.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;

@Component
@RequiredArgsConstructor
public class JWTDecoder implements JwtDecoder {

    private final JWTService jwtService;
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Override
    public Jwt decode(String token) throws JwtException {

        if (!(jwtService.verifyToken(token, false))) {
            throw new InvalidBearerTokenException("Invalid Token");
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), JWSAlgorithm.HS512.getName());
        NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
        return nimbusJwtDecoder.decode(token);
    }
}
