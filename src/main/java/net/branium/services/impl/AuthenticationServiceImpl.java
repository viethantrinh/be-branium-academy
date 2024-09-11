package net.branium.services.impl;

import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.constants.AuthorityConstants;
import net.branium.constants.RoleEnum;
import net.branium.domains.InvalidatedToken;
import net.branium.domains.Role;
import net.branium.domains.User;
import net.branium.dtos.auth.signin.SignInRequest;
import net.branium.dtos.auth.signin.SignInResponse;
import net.branium.dtos.auth.signout.SignOutRequest;
import net.branium.dtos.auth.signup.SignUpRequest;
import net.branium.dtos.auth.signup.SignUpResponse;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.Error;
import net.branium.mappers.RoleMapper;
import net.branium.repositories.InvalidatedTokenRepository;
import net.branium.repositories.RoleRepository;
import net.branium.repositories.UserRepository;
import net.branium.services.IAuthenticationService;
import net.branium.services.IJWTService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final InvalidatedTokenRepository invalidatedTokenRepo;
    private final IJWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleMapper roleMapper;

    /**
     * Check user's authentication
     *
     * @param request the sign in request
     * @return response if authenticated
     */
    @Override
    public SignInResponse signIn(SignInRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        User signInUser = userRepo.findByEmail(email)
                .orElseThrow(() -> new ApplicationException(Error.UNAUTHENTICATED));

        if (!passwordEncoder.matches(password, signInUser.getPassword())) {
            throw new ApplicationException(Error.UNAUTHENTICATED);
        }

        String accessToken = jwtService.generateToken(signInUser);

        return SignInResponse.builder()
                .token(accessToken)
                .build();
    }

    /**
     * Sign up user to system
     *
     * @param request User information
     * @return response if register successful
     */
    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new ApplicationException(Error.USER_EXISTED);
        }

        // TODO: Implement email verification here...
        Role customerRole = roleRepo.findById(RoleEnum.ROLE_CUSTOMER.getName())
                .orElseThrow(() -> new ApplicationException(Error.ROLE_NON_EXISTED));

        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .vipLevel(0)
                .enabled(false) // TODO: email verification to enable user
                .roles(Set.of(customerRole))
                .build();

        User signUpUser = userRepo.save(user);

        return SignUpResponse.builder()
                .id(signUpUser.getId())
                .username(signUpUser.getUsername())
                .email(signUpUser.getEmail())
                .firstName(signUpUser.getFirstName())
                .lastName(signUpUser.getLastName())
                .enabled(signUpUser.isEnabled())
                .gender(signUpUser.isGender())
                .birthDate(signUpUser.getBirthDate())
                .vipLevel(signUpUser.getVipLevel())
                .phoneNumber(signUpUser.getPhoneNumber())
                .roles(signUpUser.getRoles().stream()
                        .map(roleMapper::toRoleResponse)
                        .collect(Collectors.toSet()))
                .build();
    }

    /**
     * Sign out the current user
     *
     * @param request request contain user access token
     */
    @Override
    public boolean signOut(SignOutRequest request) {
        String token = request.getToken();

        if (!(jwtService.verifyToken(token, false))) {
            return false;
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
            return true;
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }

        return false;
    }


}
