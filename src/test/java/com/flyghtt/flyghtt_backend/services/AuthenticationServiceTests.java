package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.exceptions.FlyghttException;
import com.flyghtt.flyghtt_backend.exceptions.OtpException;
import com.flyghtt.flyghtt_backend.exceptions.OtpNotFoundException;
import com.flyghtt.flyghtt_backend.exceptions.UserNotFoundException;
import com.flyghtt.flyghtt_backend.models.entities.UserDetailsImpl;
import com.flyghtt.flyghtt_backend.repositories.UserOtpRepository;
import com.flyghtt.flyghtt_backend.repositories.UserRepository;
import com.flyghtt.flyghtt_backend.utils.UserTestData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTests {

    @InjectMocks
    AuthenticationService authenticationService;

    @Mock
    UserRepository userRepository;

    @Mock
    JwtService jwtService;

    @Mock
    UserOtpRepository userOtpRepository;

    @Mock
    Authentication authentication;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    SecurityContext securityContext;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    EmailService emailService;

    @Test
    void canSignUp() {

        // given
        var user = UserTestData.createNewUser();
        var otp = UserTestData.createNewUserOtp();
        var registerRequest = UserTestData.createNewRegisterRequest();

        // when
        when(userRepository.save(any())).thenReturn(user);
        when(jwtService.generateToken(any())).thenReturn(UserTestData.JWT_TOKEN);when(userOtpRepository.save(any())).thenReturn(otp);
        when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());

        var response = authenticationService.signUp(registerRequest);

        // verify
        verify(userRepository, times(1)).save(any());
        verify(jwtService, times(1)).generateToken(any());
        verify(userOtpRepository, times(1)).deleteAllByUserId(any());
        verify(userOtpRepository, times(1)).flush();
        verify(userOtpRepository, times(1)).save(any());
        verify(emailService, times(1)).sendSimpleMail(any());
        verify(passwordEncoder, times(1)).encode(user.getPassword());

        assertEquals(response.getRole(), user.getRole());
        assertFalse(response.isEmailVerified());
        assertTrue(response.isEnabled());
    }

    @Test
    void canLogin() throws UserNotFoundException {

        // given
        var user = UserTestData.getLoggedInUser().get();
        var loginRequest = UserTestData.createNewLoginRequest();

        // when
        var response = authenticationService.login(loginRequest);

        verify(jwtService, times(1)).generateToken(UserDetailsImpl.build(user));

        assertTrue(response.isEnabled());
    }

    @Test
    void canVerifyOtp() throws UserNotFoundException, OtpException, OtpNotFoundException {

        // given
        var user = UserTestData.getLoggedInUser().get();
        var otp = UserTestData.createNewUserOtp();
        var otpRequest = UserTestData.createNewOtpRequest();

        // when
        when(userOtpRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(otp));
        when(userRepository.save(any())).thenReturn(user);

        var response = authenticationService.verifyOtp(otpRequest);

        // verify
        Assertions.assertEquals(response.getUserId(), user.getUserId());
        Assertions.assertTrue(response.isEmailVerified());
        verify(userRepository, times(1)).save(any());
        verify(userOtpRepository, times(1)).deleteAllByUserId(user.getUserId());
        verify(userOtpRepository, times(1)).findByUserId(user.getUserId());
    }

    @Test
    void canResetPassword() throws FlyghttException {

        // given
        var user = UserTestData.createNewUser();
        var otp = UserTestData.createNewUserOtp();
        var request = UserTestData.createNewPasswordRequest();

        // when
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userOtpRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(otp));
        when(passwordEncoder.matches(request.getNewPassword(), user.getPassword())).thenReturn(false);
        when(passwordEncoder.encode(request.getNewPassword())).thenReturn("HASHED PASSWORD");
        when(userRepository.save(user)).thenReturn(user);

        var response = authenticationService.resetPassword(request);

        // verify
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userOtpRepository, times(1)).findByUserId(user.getUserId());
        verify(passwordEncoder, times(1)).matches(request.getNewPassword(), UserTestData.createNewUser().getPassword());
        verify(passwordEncoder, times(1)).encode(request.getNewPassword());
        verify(userRepository, times(1)).save(user);

        Assertions.assertEquals(response.getStatus(), HttpStatus.OK);
        Assertions.assertEquals(user.getPassword(), "HASHED PASSWORD");
    }

    @Test
    void canChangePassword() throws FlyghttException {

        // given
        var user = UserTestData.getLoggedInUser().get();
        var request = UserTestData.createChangePasswordRequest();

        // when
        when(passwordEncoder.matches(request.getOldPassword(), user.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(request.getNewPassword(), user.getPassword())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("HASHED PASSWORD");
        when(userRepository.save(any())).thenReturn(user);

        authenticationService.changePassword(request);

        verify(passwordEncoder, times(1)).matches(request.getOldPassword(), UserTestData.createNewUser().getPassword());
        verify(passwordEncoder, times(1)).matches(request.getNewPassword(), UserTestData.createNewUser().getPassword());
        verify(passwordEncoder, times(1)).encode(user.getPassword());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void canSendOtpToMail() throws UserNotFoundException {

        // given
        var user = UserTestData.createNewUser();
        var userOtp = UserTestData.createNewUserOtp();

        // when
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userOtpRepository.save(any())).thenReturn(userOtp);

        authenticationService.sendOtpToMailService(user.getEmail());

        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userOtpRepository, times(1)).deleteAllByUserId(user.getUserId());
        verify(userOtpRepository, times(1)).flush();
        verify(userOtpRepository, times(1)).save(any());
        verify(emailService, times(1)).sendSimpleMail(any());
    }
}
