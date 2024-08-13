package net.branium.securities;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import lombok.RequiredArgsConstructor;
import net.branium.services.IJWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;

@Component
@RequiredArgsConstructor
public class CustomJWTDecoder implements JwtDecoder {

    @Value("${jwt.secret-key}")
    private String secretKey;
    private final IJWTService jwtService;


    @Override
    public Jwt decode(String token) throws JwtException {

        try {
            jwtService.verify(token);
        } catch (JOSEException | ParseException e) {
            throw new JwtException(e.getMessage());
        }

        SecretKey secretKeySpec = new SecretKeySpec(secretKey.getBytes(), JWSAlgorithm.HS512.getName());
        NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();

        return nimbusJwtDecoder.decode(token);
    }
}
