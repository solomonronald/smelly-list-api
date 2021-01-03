package com.smellylist.api.common.models.response;

/**
 * Generic exception response.
 * Contains an errorId which is logged at server side as well as sent to user.
 * errorId can be used to search server logs for exception/stack trace.
 */
public class SimpleExceptionResponse {

    private String message;
    private String status = "ERROR";
    private String errorCode = "INTERNAL_ERROR";
    private String errorId;

    public SimpleExceptionResponse() {
        this.message = "An unexpected error has occurred";
        this.errorId = null;
    }

    public SimpleExceptionResponse(String errorId) {
        this();
        this.errorId = errorId;
    }

    public SimpleExceptionResponse(String message, String errorId) {
        this.message = message;
        this.errorId = errorId;
    }

    public SimpleExceptionResponse(String message, String status, String errorCode, String errorId) {
        this.message = message;
        this.status = status;
        this.errorCode = errorCode;
        this.errorId = errorId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
