package net.branium.securities.jwt;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class JWTAuthenticationConverter extends JwtAuthenticationConverter {

    @Override
    public void setJwtGrantedAuthoritiesConverter(Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter) {
        ((JwtGrantedAuthoritiesConverter) jwtGrantedAuthoritiesConverter).setAuthorityPrefix("");
        super.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    }
}
