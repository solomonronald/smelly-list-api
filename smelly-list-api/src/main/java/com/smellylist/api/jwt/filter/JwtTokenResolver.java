package com.smellylist.api.jwt.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class JwtTokenResolver {

    @Value("${jwt.access.secret}")
    private String jwtAccessSecret;

    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    /**
     * Resolve the JWT token from the servlet request object.
     * @param httpServletRequest Servlet Request
     * @return resolved token string from header
     */
    public String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(String token) throws UsernameNotFoundException, JWTDecodeException {
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
