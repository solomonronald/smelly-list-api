package com.smellylist.api.auth.models;

import io.swagger.annotations.ApiModelProperty;

/**
 * Object for successful sign in response.
 * Contains access and refresh token.
 */
public class SignInResponse {
    @ApiModelProperty(notes = "Short lived access token")
    private String accessToken;

    @ApiModelProperty(notes = "Long lived refresh token")
    private String refreshToken;

    public SignInResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
