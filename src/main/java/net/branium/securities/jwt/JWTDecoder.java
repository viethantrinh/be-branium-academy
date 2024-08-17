package net.branium.securities.jwt;

import com.nimbusds.jose.JWSAlgorithm;
import lombok.RequiredArgsConstructor;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.services.IJWTService;
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

    @Value("${jwt.secret-key}")
    private String secretKey;
    private final IJWTService jwtService;

    @Override
    public Jwt decode(String token) throws JwtException {

        if (!(jwtService.verifyToken(token))) {
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
