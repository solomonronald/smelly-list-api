package com.smellylist.api.auth;

import com.smellylist.api.auth.exceptions.SignInException;
import com.smellylist.api.auth.models.SignInRequest;
import com.smellylist.api.auth.models.SignInResponse;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Endpoint for user to sign in.
     * @param signInRequest username and password request
     * @return access and refresh tokens
     */
    @Operation(summary = "Sign In", description = "Allow signing in using Email/Username and password")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Sign In Successful", response = SignInResponse.class)
    })
    @PostMapping(value = "/sign_in", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signIn(@ApiParam("SignIn Request") @Valid @RequestBody SignInRequest signInRequest) throws SignInException {
        var signInResponse= authService.signIn(signInRequest);
        return new ResponseEntity<>(signInResponse, HttpStatus.OK);
    }

}
