package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import net.branium.dtos.auth.introspect.IntrospectRequest;
import net.branium.dtos.auth.introspect.IntrospectResponse;
import net.branium.dtos.auth.refreshtoken.RefreshTokenRequest;
import net.branium.dtos.auth.refreshtoken.RefreshTokenResponse;
import net.branium.dtos.auth.signin.SignInRequest;
import net.branium.dtos.auth.signin.SignInResponse;
import net.branium.dtos.auth.signout.SignOutRequest;
import net.branium.dtos.auth.signup.SignUpRequest;
import net.branium.dtos.auth.signup.SignUpResponse;
import net.branium.services.IAuthenticationService;
import net.branium.services.IJWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;
    private final IJWTService jwtService;

    @PostMapping(path = "/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
        SignInResponse response = authenticationService.signIn(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
        SignUpResponse response = authenticationService.signUp(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(path = "/sign-out")
    public ResponseEntity<Void> signOut(@RequestBody SignOutRequest request) {
        boolean signOutSucceed = authenticationService.signOut(request);
        return signOutSucceed ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }


    @PostMapping(path = "/introspect-token")
    public ResponseEntity<?> introspectToken(@RequestBody IntrospectRequest request) {
        boolean isValid = jwtService.verifyToken(request.getToken(), false);
        IntrospectResponse response = IntrospectResponse.builder()
                .valid(isValid)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshedToken = jwtService.refreshToken(request.getToken());
        RefreshTokenResponse response = RefreshTokenResponse.builder()
                .token(refreshedToken)
                .build();
        return ResponseEntity.ok(response);
    }


}
