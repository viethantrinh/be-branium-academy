package net.branium.controllers;

import net.branium.dtos.auth.request.SignInRequestDto;
import net.branium.dtos.auth.request.SignUpRequestDto;
import net.branium.dtos.auth.response.AuthenticationResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    @PostMapping(path = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseDto> signUp(@RequestBody SignUpRequestDto requestDto) {
        return null;
    }

    @PostMapping(path = "/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseDto> signIn(@RequestBody SignInRequestDto requestDto) {
        return null;
    }


}
