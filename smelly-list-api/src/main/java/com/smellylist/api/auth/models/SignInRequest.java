package com.smellylist.api.auth.models;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Object for Sing In request.
 */
public class SignInRequest {
    @NotNull
    @NotBlank
    @ApiModelProperty(notes = "Email or Username")
    private String email;

    @NotNull
    @NotBlank
    @ApiModelProperty(notes = "Password for user")
    private String password;

    public SignInRequest() {

    }

    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignInRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
