package net.branium.services.impl;

import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.constants.RoleEnum;
import net.branium.domains.InvalidatedToken;
import net.branium.domains.Role;
import net.branium.domains.User;
import net.branium.dtos.auth.signin.SignInRequest;
import net.branium.dtos.auth.signout.SignOutRequest;
import net.branium.dtos.auth.signup.SignUpRequest;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.mappers.RoleMapper;
import net.branium.repositories.InvalidatedTokenRepository;
import net.branium.repositories.RoleRepository;
import net.branium.repositories.UserRepository;
import net.branium.services.JWTService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService implements net.branium.services.AuthenticationService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final InvalidatedTokenRepository invalidatedTokenRepo;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleMapper roleMapper;

    /**
     * Check if user is authenticated
     *
     * @param request the sign in request
     * @return response a string token if authenticated
     */
    @Override
    public String signIn(SignInRequest request) {
        // extract the email and password from request
        String email = request.getEmail();
        String password = request.getPassword();

        // find the user by email, if user not existed => throw exception => unauthenticated
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ApplicationException(ErrorCode.UNAUTHENTICATED));

        // if the user existed => check the password if matches, otherwise throw exception => unauthenticated
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApplicationException(ErrorCode.UNAUTHENTICATED);
        }

        // if all above is oke => generate the access token for user
        String token = jwtService.generateToken(user);

        return token;
    }

    /**
     * Sign up user to system
     *
     * @param request Sign up information
     * @return true if register successful
     */
    @Override
    public void signUp(SignUpRequest request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new ApplicationException(ErrorCode.USER_EXISTED);
        }

        // TODO: Implement email verification here...
        Role studentRole = roleRepo.findById(RoleEnum.ROLE_STUDENT.getName())
                .orElseThrow(() -> new ApplicationException(ErrorCode.ROLE_NON_EXISTED));

        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .vipLevel(0)
                .enabled(false) // TODO: email verification to enable user
                .roles(Set.of(studentRole))
                .build();
        userRepo.save(user);
    }

    /**
     * Sign out the current user
     *
     * @param request request contain user access token
     */
    @Override
    public void signOut(SignOutRequest request) {
        String token = request.getToken();

        if (!(jwtService.verifyToken(token, false))) {
            return;
        }

        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            String jwtid = signedJWT.getJWTClaimsSet().getJWTID();
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .jwtid(jwtid)
                    .expirationTime(expirationTime)
                    .build();
            invalidatedTokenRepo.save(invalidatedToken);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
    }
}
