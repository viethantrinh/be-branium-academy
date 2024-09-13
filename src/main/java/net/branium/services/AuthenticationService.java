package net.branium.services;

import jakarta.servlet.http.HttpServletRequest;
import net.branium.dtos.auth.ResetPasswordRequest;
import net.branium.dtos.auth.SignInRequest;
import net.branium.dtos.auth.SignOutRequest;
import net.branium.dtos.auth.SignUpRequest;

public interface AuthenticationService {
    String signIn(SignInRequest request);
    void signUp(SignUpRequest request, HttpServletRequest servletRequest);
    void signOut(SignOutRequest request);
    boolean verify(String verificationCode);
    void resetPassword(String email);
    boolean updatePassword(ResetPasswordRequest request);
}
