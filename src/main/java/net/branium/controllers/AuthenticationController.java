package net.branium.controllers;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import net.branium.dtos.auth.*;
import net.branium.services.IAuthenticationService;
import net.branium.services.IJWTService;
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
    private final IJWTService jwtService;

    @PostMapping(path = "/sign-in")
    public ResponseEntity<?> signIn(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/introspect")
    public ResponseEntity<?> introspectToken(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        IntrospectResponse response = jwtService.introspectToken(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/sign-out")
    public ResponseEntity<Void> signOut(@RequestBody SignOutRequest request) {
        authenticationService.signOut(request);
        return ResponseEntity.accepted().build();
    }


}
