package net.branium.services.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.domains.InvalidatedToken;
import net.branium.domains.User;
import net.branium.dtos.auth.AuthenticationRequest;
import net.branium.dtos.auth.AuthenticationResponse;
import net.branium.dtos.auth.SignOutRequest;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.repositories.InvalidatedTokenRepository;
import net.branium.repositories.UserRepository;
import net.branium.services.IAuthenticationService;
import net.branium.services.IJWTService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final UserRepository userRepo;
    private final InvalidatedTokenRepository invalidatedTokenRepo;
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

    @Override
    public void signOut(SignOutRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = jwtService.verify(request.getToken());

        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expirationTime =  signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .JWTID(jit)
                .expirationTime(expirationTime)
                .build();

        invalidatedTokenRepo.save(invalidatedToken);
    }
}
