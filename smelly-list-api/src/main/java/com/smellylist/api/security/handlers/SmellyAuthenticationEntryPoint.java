package com.smellylist.api.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smellylist.api.common.models.response.SimpleExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SmellyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        SimpleExceptionResponse exceptionResponse = new SimpleExceptionResponse();
        exceptionResponse.setErrorCode(HttpStatus.FORBIDDEN.getReasonPhrase());
        exceptionResponse.setMessage("Authentication Required");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionResponse));
    }
}
