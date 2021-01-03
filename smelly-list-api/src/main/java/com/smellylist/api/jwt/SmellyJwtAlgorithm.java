package com.smellylist.api.jwt;

import com.auth0.jwt.algorithms.Algorithm;

public class SmellyJwtAlgorithm {
    /**
     * Configure algorithm for Access Token
     * @param secret access token secret
     * @return access token generation algorithm
     */
    public static Algorithm getAccessTokenAlgorithm(String secret) {
        return Algorithm.HMAC256(secret);
    }

    /**
     * Configure algorithm for Refresh Token
     * @param secret refresh token secret
     * @return refresh token generation algorithm
     */
    public static Algorithm getRefreshTokenAlgorithm(String secret) {
        return Algorithm.HMAC256(secret);
    }

}
