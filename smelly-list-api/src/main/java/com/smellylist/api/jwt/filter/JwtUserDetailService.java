package com.smellylist.api.jwt.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.smellylist.api.common.Constants;
import com.smellylist.api.jwt.SmellyJwtClock;
import com.smellylist.api.jwt.SmellyJwtAlgorithm;
import com.smellylist.api.roles.SmellyRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Resolve JWT access token to spring security UserDetails
 */
@Service
public class JwtUserDetailService implements UserDetailsService {

    @Value("${jwt.access.secret}")
    private String jwtAccessSecret;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException, JWTDecodeException {
        // Access token algorithm
        Algorithm accessAlgorithm = SmellyJwtAlgorithm.getAccessTokenAlgorithm(jwtAccessSecret);

        // Verify JWT
        JWTVerifier.BaseVerification verification = (JWTVerifier.BaseVerification) JWT.require(accessAlgorithm)
                .withIssuer(jwtIssuer);
        // Verify with clock shift
        JWTVerifier verifier = verification.build(new SmellyJwtClock());

        // Decoded JWT
        DecodedJWT jwt = verifier.verify(token);

        // Get public Id from JWT subject
        String publicId = jwt.getSubject();

        // Get encoded role string from JWT claims
        String roles = jwt.getClaim(Constants.ROLE_CLAIM).asString();

        // Return a spring UserDetails object
        return User.withUsername(publicId)
                .password("")
                .authorities(SmellyRole.decodeRoles(roles))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
