package net.branium.controllers;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import net.branium.dtos.auth.*;
import net.branium.services.IAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

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
    public ResponseEntity<Void> signOut(@RequestBody SignOutRequest request) throws ParseException, JOSEException {
        authenticationService.signOut(request);
        return ResponseEntity.accepted().build();
    }


    @PostMapping(path = "/introspect-token")
    public ResponseEntity<?> introspectToken(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        return null;
    }


}
