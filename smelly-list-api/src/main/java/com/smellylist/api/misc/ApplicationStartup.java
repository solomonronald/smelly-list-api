package com.smellylist.api.misc;

import com.smellylist.api.auth.AuthService;
import com.smellylist.api.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile(Constants.PROFILE_DEV)
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);

    @Autowired
    private AuthService authService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        logAccessTokens();
    }

    private void logAccessTokens() {
        String adminAccessToken = authService.generateAccessToken("testAdmin1", "amu");
        logger.info("Admin Access Token:\n" + adminAccessToken);

        String modAccessToken = authService.generateAccessToken("testMod1", "mu");
        logger.info("Mod Access Token:\n" + modAccessToken);

        String userAccessToken = authService.generateAccessToken("testUser1", "u");
        logger.info("User Access Token:\n" + userAccessToken);

    }
}
