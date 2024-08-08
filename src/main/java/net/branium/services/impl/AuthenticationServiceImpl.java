package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.User;
import net.branium.dtos.auth.AuthenticationRequest;
import net.branium.dtos.auth.AuthenticationResponse;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.repositories.UserRepository;
import net.branium.services.IAuthenticationService;
import net.branium.services.IJWTService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final IJWTService jwtService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String userEmail = request.getEmail();
        String password = request.getPassword();
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ApplicationException(Error.USER_NON_EXISTED));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApplicationException(Error.UNAUTHENTICATED);
        }
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(token).authenticated(true).build();
    }
}
