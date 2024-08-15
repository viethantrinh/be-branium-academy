package net.branium.services.impl;

import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.constants.AuthorityConstants;
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
import net.branium.services.IAuthenticationService;
import net.branium.services.IJWTService;
import net.branium.services.IRoleService;
import net.branium.services.IUserService;
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
    private final IUserService userService;
    private final IRoleService roleService;
    private final InvalidatedServiceImpl invalidatedService;
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

    /**
     * Sign up user to system
     *
     * @param request User information
     * @return response if register successful
     */
    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new ApplicationException(Error.USER_EXISTED);
        }

        // TODO: Implement email verification here...
        Role customerRole = roleService.getByName(AuthorityConstants.ROLE_CUSTOMER);
        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .vipLevel(0)
                .enabled(false) // TODO: email verification to enable user
                .roles(Set.of(customerRole))
                .build();

        User signUpUser = userService.createCustomer(user);
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
    public void signOut(SignOutRequest request) {
        String token = request.getAccessToken();

        if (!jwtService.verifyToken(token)) {
            throw new ApplicationException(Error.INVALID_TOKEN);
        }

        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            String jwtid = signedJWT.getJWTClaimsSet().getJWTID();
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .jwtid(jwtid)
                    .expirationTime(expirationTime)
                    .build();
            invalidatedService.create(invalidatedToken);
        } catch (ParseException e) {
            throw new ApplicationException(Error.INVALID_TOKEN);
        }
    }


}
