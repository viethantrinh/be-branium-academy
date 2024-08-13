package net.branium.services;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import net.branium.domains.User;
import net.branium.dtos.auth.IntrospectRequest;
import net.branium.dtos.auth.IntrospectResponse;

import java.text.ParseException;

public interface IJWTService {
    String generateToken(User user);
    IntrospectResponse introspectToken(IntrospectRequest request) throws ParseException, JOSEException;
    SignedJWT verify(String token) throws JOSEException, ParseException;
}
