package com.smellylist.api.jwt.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smellylist.api.common.models.response.SimpleExceptionResponse;
import com.smellylist.api.exceptions.handlers.SmellyEntityExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        try {
            if (token != null) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                // this is very important, since it guarantees the user is not authenticated at all
                SecurityContextHolder.clearContext();
            }
        } catch (TokenExpiredException ex) {
            // Clear Context
            SecurityContextHolder.clearContext();

            setExpiredJwtResponse(httpServletResponse);
            return;
        } catch (JWTDecodeException ex) {
            // Clear Context
            SecurityContextHolder.clearContext();

            setJwtErrorResponse(httpServletResponse, ex);
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    private void setExpiredJwtResponse(HttpServletResponse httpServletResponse) throws IOException {
        SimpleExceptionResponse exceptionResponse = new SimpleExceptionResponse();
        exceptionResponse.setErrorCode("TOKEN_EXPIRED");
        exceptionResponse.setMessage("The provided token is expired.");

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(exceptionResponse));
    }

    private void setJwtErrorResponse(HttpServletResponse httpServletResponse, JWTDecodeException ex) throws IOException {
        SimpleExceptionResponse exceptionResponse = new SimpleExceptionResponse();
        exceptionResponse.setErrorCode(HttpStatus.FORBIDDEN.getReasonPhrase());
        exceptionResponse.setMessage("Access Denied");

        String uuid = UUID.randomUUID().toString();
        exceptionResponse.setErrorId(uuid);
        logger.error("[" + uuid + "] " + ex.getMessage());

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(exceptionResponse));
    }
}
