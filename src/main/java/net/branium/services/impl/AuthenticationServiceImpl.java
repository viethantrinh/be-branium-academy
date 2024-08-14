package net.branium.services.impl;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.User;
import net.branium.dtos.auth.*;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.services.IAuthenticationService;
import net.branium.services.IJWTService;
import net.branium.services.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserService userService;
    private final IJWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SignInResponse signIn(SignInRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        User signInUser = userService.getByEmail(email);

        if (!passwordEncoder.matches(password, signInUser.getPassword())) {
            throw new ApplicationException(Error.UNAUTHENTICATED);
        }

        String accessToken = jwtService.generateToken(signInUser);

        return SignInResponse.builder()
                .accessToken(accessToken)
                .authenticated(true)
                .build();
    }

    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new ApplicationException(Error.USER_EXISTED);
        }
        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        User registeredUser = userService.signUp(user);

        return null;
    }

    @Override
    public void signOut(SignOutRequest request) throws ParseException, JOSEException {

    }

}
