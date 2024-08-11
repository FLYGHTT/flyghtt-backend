package com.flyghtt.flyghtt_backend.utils;

import com.flyghtt.flyghtt_backend.exceptions.UserNotFoundException;
import com.flyghtt.flyghtt_backend.models.entities.EmailDetails;
import com.flyghtt.flyghtt_backend.models.entities.Role;
import com.flyghtt.flyghtt_backend.models.entities.User;
import com.flyghtt.flyghtt_backend.models.entities.UserDetailsImpl;
import com.flyghtt.flyghtt_backend.models.entities.UserOtp;
import com.flyghtt.flyghtt_backend.models.requests.LoginRequest;
import com.flyghtt.flyghtt_backend.models.requests.OtpRequest;
import com.flyghtt.flyghtt_backend.models.requests.PasswordResetRequest;
import com.flyghtt.flyghtt_backend.models.requests.RegisterRequest;
import com.flyghtt.flyghtt_backend.services.utils.UserUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class UserTestData {

    public static final UUID USER_ID = UUID.fromString("11111111-0000-0000-0000-000000000000");
    public static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpdHptaGl6dGVybG91aXNAZ21haWwuY29tIiwiaWF0IjoxNzIzMjg0Mjc3LCJleHAiOjE3MjMyODc4Nzd9.cYXCzRgq8X6I1EG4i4SfCZMdRsuqgAjVttfVSTWitxU";
    public static final int OTP = 101010;

    private UserTestData() {}

    public static User createNewUser() {

        return User.builder()
                .userId(USER_ID)
                .firstName("Itsuokor")
                .lastName("Marvellous")
                .email("itzmhizterlouis@gmail.com")
                .role(Role.USER)
                .password("password123")
                .build();
    }

    public static UserOtp createNewUserOtp() {

        return UserOtp.builder()
                .userId(USER_ID)
                .otp(OTP)
                .expiryDate(DateUtils.addMinutes(new Date(), -12))
                .id(1)
                .build();
    }

    public static EmailDetails createNewEmailDetails() {

        return EmailDetails.builder()
                .messageBody("Something to think about")
                .recipient("Marvellous")
                .subject("Unit Test")
                .build();
    }

    public static RegisterRequest createNewRegisterRequest() {

        var request = new RegisterRequest();
        request.setEmail("itzmhizterlouis@gmail.com");
        request.setPassword("password123");
        request.setFirstName("Itsuokor");
        request.setLastName("Marvellous");
        request.setRole(Role.USER);

        return request;
    }

    public static Optional<User> getLoggedInUser() throws UserNotFoundException {
        // Create a mock Authentication object
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        var user = UserTestData.createNewUser();
        when(authentication.getPrincipal()).thenReturn(UserDetailsImpl.build(user));

        // Create a mock SecurityContext and set the mock Authentication
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Call the method to retrieve the logged-in user
        return UserUtil.getLoggedInUser();
    }

    public static LoginRequest createNewLoginRequest() {

        LoginRequest request = new LoginRequest();

        request.setEmail("itzmhizterlouis@gmail.com");
        request.setPassword("password123");

        return request;
    }

    public static OtpRequest createNewOtpRequest() {

        OtpRequest request = new OtpRequest();

        request.setOtp(OTP);

        return request;
    }

    public static PasswordResetRequest createNewPasswordRequest() {

        PasswordResetRequest request = new PasswordResetRequest();

        request.setEmail("itzmhizterlouis@gmail.com");
        request.setOtp(OTP);
        request.setNewPassword("new password");
        request.setConfirmNewPassword("new password");

        return request;
    }
}
