package net.branium.services.impl;

import com.nimbusds.jwt.SignedJWT;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.branium.constants.RoleEnum;
import net.branium.domains.InvalidatedToken;
import net.branium.domains.Role;
import net.branium.domains.User;
import net.branium.dtos.auth.signin.SignInRequest;
import net.branium.dtos.auth.signout.SignOutRequest;
import net.branium.dtos.auth.signup.SignUpRequest;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.repositories.InvalidatedTokenRepository;
import net.branium.repositories.RoleRepository;
import net.branium.repositories.UserRepository;
import net.branium.services.AuthenticationService;
import net.branium.services.JWTService;
import net.branium.utils.RandomGenerateUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final InvalidatedTokenRepository invalidatedTokenRepo;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    /**
     * Check if user is authenticated
     *
     * @param request the sign in request
     * @return response a string token if authenticated
     */
    @Override
    public String signIn(SignInRequest request) {
        // extract the email and password from request
        String email = request.getEmail();
        String password = request.getPassword();

        // find the user by email, if user not existed => throw exception => unauthenticated
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ApplicationException(ErrorCode.UNAUTHENTICATED));

        // if the user existed => check the password if matches, otherwise throw exception => unauthenticated
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApplicationException(ErrorCode.UNAUTHENTICATED);
        }

        // if all above is oke => generate the access token for user
        String token = jwtService.generateToken(user);

        return token;
    }

    /**
     * Sign up user to system
     *
     * @param request Sign up information
     * @return true if register successful
     */
    @Override
    public void signUp(SignUpRequest request, HttpServletRequest servletRequest) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new ApplicationException(ErrorCode.USER_EXISTED);
        }

        Role studentRole = roleRepo.findById(RoleEnum.ROLE_STUDENT.getName())
                .orElseThrow(() -> new ApplicationException(ErrorCode.UNCATEGORIZED_ERROR));

        // generate the code for email verification
        String randomCode = RandomGenerateUtils.randomAlphanumericString(64);

        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .vipLevel(0)
                .enabled(false) // TODO: email verification to enable user
                .roles(Set.of(studentRole))
                .verificationCode(randomCode)
                .build();
        User savedUser = userRepo.save(user);

        // send the email to the user's email
        String siteUrl = servletRequest.getRequestURL().toString();
        String s = siteUrl.replace(servletRequest.getServletPath(), "");
        try {
            sendVerificationEmail(savedUser, s);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendVerificationEmail(User user, String siteUrl) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "hntrnn12@gmail.com";
        String senderName = "Branium Academy";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Branium Academy.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", user.getLastName() + " " + user.getFirstName());
        String verifyURL = siteUrl + "/auth/verify?code=" + user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);
        mailSender.send(message);
    }

    /**
     * Sign out the current user
     *
     * @param request request contain user access token
     */
    @Override
    public void signOut(SignOutRequest request) {
        String token = request.getToken();

        if (!(jwtService.verifyToken(token, false))) {
            return;
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
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean verify(String verificationCode) {
        if (verificationCode.length() == 64) {
            Optional<User> userOptional = userRepo.findByVerificationCode(verificationCode);

            if (userOptional.isEmpty()) {
                return false;
            }

            User user = userOptional.get();

            if (!user.getVerificationCode().equals(verificationCode)) {
                return false;
            }

            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepo.save(user);

            return true;
        } else if (verificationCode.length() == 6) {
            // TODO: logic for reset password
            return true;
        }
        return false;
    }
}
