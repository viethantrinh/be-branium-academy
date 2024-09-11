package net.branium.services;

import net.branium.dtos.auth.signin.SignInRequest;
import net.branium.dtos.auth.signout.SignOutRequest;
import net.branium.dtos.auth.signup.SignUpRequest;

public interface AuthenticationService {
    String signIn(SignInRequest request);
    void signUp(SignUpRequest request);
    void signOut(SignOutRequest request);
}
