package com.smellylist.api.auth;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.smellylist.api.auth.exceptions.SignInException;
import com.smellylist.api.auth.models.SignInRequest;
import com.smellylist.api.auth.models.SignInResponse;

/**
 * Auth service to generate Access and Refresh JWTs.
 */
public interface AuthService {

    SignInResponse signIn(SignInRequest signInRequest) throws SignInException;

    String generateAccessToken(String publicUserId, int encodedRole) throws JWTCreationException;

    String generateRefreshToken(String publicUserId, int encodedRole) throws JWTCreationException;

}
