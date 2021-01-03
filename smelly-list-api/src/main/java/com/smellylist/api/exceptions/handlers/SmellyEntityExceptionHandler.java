package com.smellylist.api.exceptions.handlers;

import com.smellylist.api.common.models.response.SimpleExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.UUID;

@EnableWebMvc
@ControllerAdvice(basePackages = "com.smellylist.api")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SmellyEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(SmellyEntityExceptionHandler.class);

    /**
     * Handles uncaught exceptions for which explicit handler is not mentioned.
     * Avoids showing error to user, instead provides a UUID.
     * UUID is logged with the exception/stack trace in server logs.
     * Alternatively the exception can be searched in logs via UUID provided in response.
     * @param ex the uncaught exception
     * @return SimpleExceptionResponse ResponseEntity
     */
    @ExceptionHandler(value = Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        String uuid = UUID.randomUUID().toString();
        SimpleExceptionResponse simpleExceptionResponse = new SimpleExceptionResponse(uuid);
        logger.error("[" + uuid + "]", ex);
        return new ResponseEntity<>(simpleExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // TODO: Add Access Denied handler
}
