package com.smellylist.api.auth.exceptions.handlers;

import com.smellylist.api.auth.exceptions.SignInException;
import com.smellylist.api.common.ErrorCode;
import com.smellylist.api.common.models.response.SimpleExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice(basePackages = "com.smellylist.api.auth")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthExceptionHandler {

    @ExceptionHandler(value = SignInException.class)
    public final ResponseEntity<Object> handleSignInException(SignInException ex) {
        SimpleExceptionResponse simpleExceptionResponse = new SimpleExceptionResponse();
        simpleExceptionResponse.setMessage(ex.getMessage());
        simpleExceptionResponse.setErrorCode(ErrorCode.USERNAME_OR_PASSWORD_INCORRECT.name());
        return new ResponseEntity<>(simpleExceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleAllExceptions(MethodArgumentNotValidException ex) {
        SimpleExceptionResponse simpleExceptionResponse = new SimpleExceptionResponse();
        simpleExceptionResponse.setMessage("Invalid Request");
        simpleExceptionResponse.setErrorCode(HttpStatus.BAD_REQUEST.name());
        return new ResponseEntity<>(simpleExceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
