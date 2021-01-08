package com.smellylist.api.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.smellylist.api.auth.exceptions.SignInException;
import com.smellylist.api.auth.models.SignInRequest;
import com.smellylist.api.auth.models.SignInResponse;
import com.smellylist.api.auth.models.UserCredential;
import com.smellylist.api.common.Constants;
import com.smellylist.api.jwt.SmellyJwtAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AuthServiceImpl implements AuthService {
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

    @Autowired
    UserCredentialRepository userCredentialRepository;

    public SignInResponse signIn(SignInRequest signInRequest) throws SignInException {

        UserCredential userCredential;

        // If sign in request contains '@' search by email, else search by username.
        if (!signInRequest.getEmail().startsWith("@") && signInRequest.getEmail().contains("@")) {
            userCredential = userCredentialRepository.findByEmail(signInRequest.getEmail());
        } else {
            String username = signInRequest.getEmail().replace("@", "").trim();
            userCredential = userCredentialRepository.findByUsername(username);
        }

        if (userCredential != null) {
            String publicUserId = userCredential.getPublicId();
            int encodedRole = userCredential.getRoleMask();

            try {
                String accessToken = this.generateAccessToken(publicUserId, encodedRole);
                String refreshToken = this.generateRefreshToken(publicUserId, encodedRole);

                return new SignInResponse(accessToken, refreshToken);
            } catch(JWTCreationException e) {
                throw new SignInException("Username or password is incorrect.");
            }
        } else {
            throw new SignInException("Username or password is incorrect.");
        }
    }

    public String generateAccessToken(String publicUserId, int encodedRole) throws JWTCreationException {

        Algorithm algorithm = SmellyJwtAlgorithm.getAccessTokenAlgorithm(jwtAccessSecret);

        // Convert local date to UTC
        LocalDateTime expiryDateTime = LocalDateTime.now(ZoneOffset.UTC).plus(Duration.of(jwtAccessExpiryMinutes, ChronoUnit.MINUTES));
        // Convert expiry LocalDateTime to Date
        Date expiryDate = Date.from(expiryDateTime.atZone(ZoneId.systemDefault()).toInstant());

        return JWT.create()
                .withExpiresAt(expiryDate)
                .withSubject(publicUserId)
                .withClaim(Constants.ROLE_CLAIM, encodedRole)
                .sign(algorithm);
    }

    public String generateRefreshToken(String publicUserId, int encodedRole) throws JWTCreationException {

        Algorithm algorithm = SmellyJwtAlgorithm.getRefreshTokenAlgorithm(jwtRefreshSecret);

        // Convert local date to UTC
        LocalDateTime expiryDateTime = LocalDateTime.now(ZoneOffset.UTC).plus(Duration.of(jwtRefreshExpiryDays, ChronoUnit.DAYS));
        // Convert expiry LocalDateTime to Date
        Date expiryDate = Date.from(expiryDateTime.atZone(ZoneId.systemDefault()).toInstant());

        return JWT.create()
                .withExpiresAt(expiryDate)
                .withSubject(publicUserId)
                .withClaim(Constants.ROLE_CLAIM, encodedRole)
                .sign(algorithm);
    }

}
