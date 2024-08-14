package net.branium.services;

import com.nimbusds.jose.JOSEException;
import net.branium.dtos.auth.*;

import java.text.ParseException;

public interface IAuthenticationService {
    SignInResponse signIn(SignInRequest request);
    SignUpResponse signUp(SignUpRequest request);
    void signOut(SignOutRequest request) throws ParseException, JOSEException;
}
