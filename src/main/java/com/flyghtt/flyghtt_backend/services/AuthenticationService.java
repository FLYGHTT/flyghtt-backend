package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.exceptions.FlyghttException;
import com.flyghtt.flyghtt_backend.exceptions.OtpException;
import com.flyghtt.flyghtt_backend.exceptions.UserNotFoundException;
import com.flyghtt.flyghtt_backend.models.entities.EmailDetails;
import com.flyghtt.flyghtt_backend.models.entities.User;
import com.flyghtt.flyghtt_backend.models.entities.UserDetailsImpl;
import com.flyghtt.flyghtt_backend.models.requests.LoginRequest;
import com.flyghtt.flyghtt_backend.models.requests.OtpRequest;
import com.flyghtt.flyghtt_backend.models.requests.PasswordResetRequest;
import com.flyghtt.flyghtt_backend.models.requests.RegisterRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.AuthenticationResponse;
import com.flyghtt.flyghtt_backend.repositories.UserRepository;
import com.flyghtt.flyghtt_backend.services.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse signUp(RegisterRequest request) {

        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .emailVerified(false)
                .enabled(true)
                .build();

        userRepository.save(user);

        var jwtToken = generateTokenWithOtp(UserDetailsImpl.build(user));

        return buildResponse(user, jwtToken);
    }

    public AuthenticationResponse login(LoginRequest request) throws UserNotFoundException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = UserUtil.getLoggedInUser().get();

        String jwtToken;

        if (!user.isEmailVerified()) {

            jwtToken = generateTokenWithOtp(UserDetailsImpl.build(user));
        } else {

            jwtToken = jwtService.generateToken(UserDetailsImpl.build(user));
        }

        return buildResponse(user, jwtToken);
    }


    public AuthenticationResponse verifyOtp(OtpRequest request, String token) throws UserNotFoundException, OtpException {

        Map<String, Object> claims = extractMapFromJwt(token);
        int otp = (int) claims.get("otp");
        Date expiryDate = new Date(TimeUnit.SECONDS.toMillis((long)claims.get("expiryDate")));

        User user = UserUtil.getLoggedInUser().get();

        throwErrorIfOtpNotValid(otp, request.getOtp(), expiryDate);

        user.setEmailVerified(true);
        userRepository.save(user);

        return buildResponse(user, jwtService.generateToken(UserDetailsImpl.build(user)));
    }

    public AuthenticationResponse sendOtpForResetPassword(String email) throws UserNotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        String jwt = generateTokenWithOtp(UserDetailsImpl.build(user));

        return buildResponse(user, jwt);
    }

    public AppResponse resetPassword(PasswordResetRequest request, String token) throws FlyghttException {

        Map<String, Object> claims = extractMapFromJwt(token);
        int otp = (int) claims.get("otp");
        Date expiryDate = new Date(TimeUnit.SECONDS.toMillis((long)claims.get("expiryDate")));

        User user = UserUtil.getLoggedInUser().get();

        throwErrorIfOtpNotValid(otp, request.getOtp(), expiryDate);

        if (request.getConfirmNewPassword().equals(request.getNewPassword())) {

            if (!passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {

                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                userRepository.save(user);

                return AppResponse.builder()
                        .message("Password has been successfully reset")
                        .status(HttpStatus.OK)
                        .build();
            } else {

                throw new FlyghttException("Old password cannot be the same with new password");
            }
        } else {

            throw new FlyghttException("Passwords do not match");
        }
    }

    public AppResponse changePassword(PasswordResetRequest request) throws FlyghttException {

        User user = UserUtil.getLoggedInUser().get();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {

            throw new FlyghttException("Incorrect Former Password");
        }

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {

            throw new FlyghttException(("New passwords do not match"));
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {

            throw new FlyghttException("Old password cannot be the same with new password");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return AppResponse.builder()
                .message("Passwords has been successfully changed")
                .status(HttpStatus.OK)
                .build();
    }

    private String generateTokenWithOtp(UserDetailsImpl userDetails) {

        Random random = new Random();
        int otp = random.nextInt(100000, 1000000);
        Date expiryDate = DateUtils.addMinutes(new Date(), 15);

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("otp", otp);
        extraClaims.put("expiryDate", expiryDate);

        sendOtpToMail(otp, userDetails.getEmail());

        return jwtService.generateToken(extraClaims, userDetails);
    }

    private void sendOtpToMail(int otp, String recipientEmail) {

        emailService.sendSimpleMail(
                EmailDetails.builder()
                        .messageBody(Integer.toString(otp))
                        .subject("Your OTP for FLYGHTT")
                        .recipient(recipientEmail)
                        .build()
        );
    }

    private AuthenticationResponse buildResponse(User user, String jwtToken) {

        return AuthenticationResponse.builder()
                .userId(user.getUserId())
                .token(jwtToken)
                .emailVerified(user.isEmailVerified())
                .enabled(user.isEnabled())
                .role(user.getRole())
                .build();
    }

    private Map<String, Object> extractMapFromJwt(String token) {

        String jwt = token.substring(7);
        return jwtService.extractAllClaims(jwt);
    }

    private void throwErrorIfOtpNotValid(int otp, int requestOtp, Date expiryDate) throws OtpException {

        if (!(otp == requestOtp && !expiryDate.before(new Date()))) {

            throw new OtpException();
        }
    }
}
