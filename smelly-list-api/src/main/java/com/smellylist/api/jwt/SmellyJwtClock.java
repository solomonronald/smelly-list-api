package com.smellylist.api.jwt;

import com.auth0.jwt.interfaces.Clock;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * Auth0 JWT Clock Implementation to convert UTC time to local server time received from JWT token expiry.
 */
public class SmellyJwtClock implements Clock {

    /**
     * Shift Local Time with UTC for decoding JWT token.
     * @return Shifted Date.
     */
    @Override
    public Date getToday() {
        return Date.from(LocalDateTime.now(ZoneOffset.UTC).atZone(ZoneId.systemDefault()).toInstant());
    }
}
