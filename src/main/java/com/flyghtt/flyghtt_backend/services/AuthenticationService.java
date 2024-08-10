package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.exceptions.FlyghttException;
import com.flyghtt.flyghtt_backend.exceptions.OtpException;
import com.flyghtt.flyghtt_backend.exceptions.OtpNotFoundException;
import com.flyghtt.flyghtt_backend.exceptions.UserNotFoundException;
import com.flyghtt.flyghtt_backend.models.entities.EmailDetails;
import com.flyghtt.flyghtt_backend.models.entities.UserOtp;
import com.flyghtt.flyghtt_backend.models.entities.User;
import com.flyghtt.flyghtt_backend.models.entities.UserDetailsImpl;
import com.flyghtt.flyghtt_backend.models.requests.ChangePasswordRequest;
import com.flyghtt.flyghtt_backend.models.requests.LoginRequest;
import com.flyghtt.flyghtt_backend.models.requests.OtpRequest;
import com.flyghtt.flyghtt_backend.models.requests.PasswordResetRequest;
import com.flyghtt.flyghtt_backend.models.requests.RegisterRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.AuthenticationResponse;
import com.flyghtt.flyghtt_backend.models.response.VerifyOtpResponse;
import com.flyghtt.flyghtt_backend.repositories.UserOtpRepository;
import com.flyghtt.flyghtt_backend.repositories.UserRepository;
import com.flyghtt.flyghtt_backend.services.utils.UserUtil;
import jakarta.transaction.Transactional;
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
import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final UserOtpRepository userOtpRepository;

    @Transactional
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

        var jwtToken = jwtService.generateToken(UserDetailsImpl.build(user));

        generateOtp(user);

        return buildResponse(user, jwtToken);
    }

    @Transactional
    public AuthenticationResponse login(LoginRequest request) throws UserNotFoundException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = UserUtil.getLoggedInUser().get();

        if (!user.isEmailVerified()) {

            generateOtp(user);
        }
        String jwtToken = jwtService.generateToken(UserDetailsImpl.build(user));

        return buildResponse(user, jwtToken);
    }

    @Transactional
    public VerifyOtpResponse verifyOtp(OtpRequest request) throws UserNotFoundException, OtpException, OtpNotFoundException {

        User user = UserUtil.getLoggedInUser().get();

        UserOtp otp = userOtpRepository.findByUserId(user.getUserId()).orElseThrow(OtpNotFoundException::new);

        throwErrorIfOtpNotValid(otp, request.getOtp());

        userOtpRepository.deleteAllByUserId(user.getUserId());

        user.setEmailVerified(true);
        userRepository.save(user);

        return VerifyOtpResponse.builder()
                .userId(user.getUserId())
                .enabled(user.isEnabled())
                .emailVerified(user.isEmailVerified())
                .role(user.getRole())
                .build();
    }

    @Transactional
    public AppResponse resetPassword(PasswordResetRequest request) throws FlyghttException {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(UserNotFoundException::new);

        UserOtp userOtp = userOtpRepository.findByUserId(user.getUserId()).orElseThrow(OtpNotFoundException::new);

        throwErrorIfOtpNotValid(userOtp, request.getOtp());

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

    @Transactional
    public AppResponse changePassword(ChangePasswordRequest request) throws FlyghttException {

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

    @Transactional
    public AppResponse sendOtpToMailService(String email) throws UserNotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        int otp = generateOtp(user);

        sendOtpToMail(otp, user.getEmail());

        return AppResponse.builder()
                .message("OTP has been successfully sent")
                .status(HttpStatus.OK)
                .build();
    }

    private void sendOtpToMail(int otp, String recipientEmail) {

        emailService.sendSimpleMail(
                EmailDetails.builder()
                        .messageBody("Your OTP for FLYGHTT is " + otp)
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

    private void throwErrorIfOtpNotValid(UserOtp userOtp, int requestOtp) throws OtpException {

        if (!(userOtp.getOtp() == requestOtp && !userOtp.getExpiryDate().before(new Date()))) {

            throw new OtpException();
        }
    }

    private int generateOtp(User user) {

        Random random = new Random();
        int pin = random.nextInt(100000, 1000000);

        userOtpRepository.deleteAllByUserId(user.getUserId());
        userOtpRepository.flush();

        UserOtp userOtp = UserOtp.builder()
                .otp(pin)
                .userId(user.getUserId())
                .expiryDate(DateUtils.addMinutes(new Date(), 15))
                .build();

        userOtpRepository.save(userOtp);

        sendOtpToMail(pin, user.getEmail());

        return pin;
    }
}
