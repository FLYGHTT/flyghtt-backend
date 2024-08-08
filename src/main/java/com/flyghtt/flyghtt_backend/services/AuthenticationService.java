package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.models.entities.EmailDetails;
import com.flyghtt.flyghtt_backend.models.entities.User;
import com.flyghtt.flyghtt_backend.models.entities.UserDetailsImpl;
import com.flyghtt.flyghtt_backend.models.requests.RegisterRequest;
import com.flyghtt.flyghtt_backend.models.response.AuthenticationResponse;
import com.flyghtt.flyghtt_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;

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

        return AuthenticationResponse.builder()
                .userId(user.getUserId())
                .token(jwtToken)
                .emailVerified(user.isEmailVerified())
                .enabled(user.isEnabled())
                .build();
    }

    private String generateTokenWithOtp(UserDetailsImpl userDetails) {

        Random random = new Random();
        int otp = random.nextInt(100000, 1000000);
        Date expiryDate = DateUtils.addMinutes(new Date(), 15);

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("otp", otp);
        extraClaims.put("expiryDate", expiryDate);

//        sendOtpToMail(otp, userDetails.getEmail());

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
}
