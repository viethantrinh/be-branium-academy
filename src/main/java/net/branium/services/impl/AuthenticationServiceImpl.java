package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.services.IAuthenticationService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


}
