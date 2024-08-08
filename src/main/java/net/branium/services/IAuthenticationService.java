package net.branium.services;

import net.branium.dtos.auth.AuthenticationRequest;
import net.branium.dtos.auth.AuthenticationResponse;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
