package com.smellylist.api.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.smellylist.api.common.Constants;
import com.smellylist.api.jwt.SmellyJwtAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Auth service to generate Access and Refresh JWTs.
 */
@Service
public class AuthService {
    @Value("${jwt.access.secret}")
    private String jwtAccessSecret;

    @Value("${jwt.refresh.secret}")
    private String jwtRefreshSecret;

    @Value("${jwt.access.expiry.minutes}")
    private int jwtAccessExpiryMinutes;

    @Value("${jwt.refresh.expiry.days}")
    private int jwtRefreshExpiryDays;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    public String generateAccessToken(String publicUserId, String encodedRole) throws JWTCreationException {

        Algorithm algorithm = SmellyJwtAlgorithm.getAccessTokenAlgorithm(jwtAccessSecret);

        // Convert local date to UTC
        LocalDateTime expiryDateTime = LocalDateTime.now(ZoneOffset.UTC).plus(Duration.of(jwtAccessExpiryMinutes, ChronoUnit.MINUTES));

        return createJwt(algorithm, expiryDateTime, publicUserId, encodedRole);
    }

    public String generateRefreshToken(String publicUserId, String encodedRole) throws JWTCreationException {

        Algorithm algorithm = SmellyJwtAlgorithm.getRefreshTokenAlgorithm(jwtRefreshSecret);

        // Convert local date to UTC
        LocalDateTime expiryDateTime = LocalDateTime.now(ZoneOffset.UTC).plus(Duration.of(jwtRefreshExpiryDays, ChronoUnit.DAYS));

        return createJwt(algorithm, expiryDateTime, publicUserId, encodedRole);
    }

    /**
     * Generate a JWT.
     * @param algorithm Algorithm to sign JWT with
     * @param expiryDateTime Expiry LocalDateTime (shifted to UTC time)
     * @param publicUserId Will be used as subject of JWT
     * @param encodedRoles Encoded SmellyRole string
     * @return JWT String
     */
    private String createJwt(
            Algorithm algorithm,
            LocalDateTime expiryDateTime,
            String publicUserId,
            String encodedRoles
    ) throws JWTCreationException {

        // Convert expiry LocalDateTime to Date
        Date expiryDate = Date.from(expiryDateTime.atZone(ZoneId.systemDefault()).toInstant());

        return JWT.create()
                .withIssuer(jwtIssuer)
                .withExpiresAt(expiryDate)
                .withSubject(publicUserId)
                .withClaim(Constants.ROLE_CLAIM, encodedRoles)
                .sign(algorithm);
    }

}
