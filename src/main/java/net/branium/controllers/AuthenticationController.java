package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import net.branium.dtos.auth.AuthenticationRequest;
import net.branium.dtos.auth.AuthenticationResponse;
import net.branium.dtos.auth.IntrospectRequest;
import net.branium.dtos.auth.IntrospectResponse;
import net.branium.services.IAuthenticationService;
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

    @PostMapping(path = "/sign-in")
    public ResponseEntity<?> signIn(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/introspect")
    public ResponseEntity<?> introspectToken(@RequestBody IntrospectRequest request) {
        IntrospectResponse response = authenticationService.introspectToken(request);
        return ResponseEntity.ok(response);
    }


}
