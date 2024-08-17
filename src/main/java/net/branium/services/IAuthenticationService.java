package net.branium.services;

import net.branium.dtos.auth.signin.SignInRequest;
import net.branium.dtos.auth.signin.SignInResponse;
import net.branium.dtos.auth.signout.SignOutRequest;
import net.branium.dtos.auth.signup.SignUpRequest;
import net.branium.dtos.auth.signup.SignUpResponse;

public interface IAuthenticationService {
    SignInResponse signIn(SignInRequest request);
    SignUpResponse signUp(SignUpRequest request);
    boolean signOut(SignOutRequest request);
}
