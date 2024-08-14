package net.branium.securities.jwt;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Component
public class JWTDecoder implements JwtDecoder {
    @Override
    public Jwt decode(String token) throws JwtException {
        return null;
    }
}
