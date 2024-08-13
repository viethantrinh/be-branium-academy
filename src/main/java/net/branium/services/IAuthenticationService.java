package net.branium.services;

import com.nimbusds.jose.JOSEException;
import net.branium.dtos.auth.AuthenticationRequest;
import net.branium.dtos.auth.AuthenticationResponse;
import net.branium.dtos.auth.SignOutRequest;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    void signOut(SignOutRequest request) throws ParseException, JOSEException;
}
