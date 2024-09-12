package net.branium.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.branium.dtos.auth.refreshtoken.RefreshTokenRequest;
import net.branium.dtos.auth.refreshtoken.RefreshTokenResponse;
import net.branium.dtos.auth.signin.SignInRequest;
import net.branium.dtos.auth.signin.SignInResponse;
import net.branium.dtos.auth.signout.SignOutRequest;
import net.branium.dtos.auth.signup.SignUpRequest;
import net.branium.dtos.base.ApiResponse;
import net.branium.services.AuthenticationService;
import net.branium.services.JWTService;
import net.branium.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JWTService jwtService;

    @PostMapping(path = "/sign-in")
    public ResponseEntity<ApiResponse<SignInResponse>> signIn(@Valid @RequestBody SignInRequest request) {
        String token = authenticationService.signIn(request);
        ApiResponse<SignInResponse> response = ApiResponse.<SignInResponse>builder()
                .message("sign in success")
                .result(SignInResponse.builder()
                        .token(token)
                        .build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/sign-up")
    public ResponseEntity<ApiResponse<?>> signUp(@Valid @RequestBody SignUpRequest request, HttpServletRequest servletRequest) {
        authenticationService.signUp(request, servletRequest);
        ApiResponse<?> response = ApiResponse.builder()
                .message("sign up succeeded")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(path = "/sign-out")
    public ResponseEntity<Void> signOut(@RequestBody SignOutRequest request) {
        authenticationService.signOut(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/refresh-token")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshedToken = jwtService.refreshToken(request.getToken());
        ApiResponse<RefreshTokenResponse> response = ApiResponse.<RefreshTokenResponse>builder()
                .message("refresh the token success")
                .result(RefreshTokenResponse.builder()
                        .token(refreshedToken)
                        .build())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/verify", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> verify(@RequestParam(name = "code") String code) {
        boolean verified = authenticationService.verify(code);
        return verified
                ? ResponseEntity.ok("<h1>verify successful. You can sign in now!</h1>")
                : ResponseEntity.badRequest().body("<h1 style='color: red'>verify failed. Try again!</h1>");
    }




}
