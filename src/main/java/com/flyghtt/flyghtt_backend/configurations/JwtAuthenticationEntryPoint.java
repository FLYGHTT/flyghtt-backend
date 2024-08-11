package com.flyghtt.flyghtt_backend.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String JWT_GENERIC_ERROR = "Unable to authenticate request";

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException ex) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        log.error(ex.getMessage(), ex);
        String jsonErrorResponse = OBJECT_MAPPER.writeValueAsString(AppResponse.builder().message(JWT_GENERIC_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        httpServletResponse.getWriter().write(jsonErrorResponse);
    }
}
