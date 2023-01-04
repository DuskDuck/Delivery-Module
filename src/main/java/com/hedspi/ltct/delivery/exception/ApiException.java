package com.hedspi.ltct.delivery.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private final String reason;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
    private final Boolean success;

    public ApiException(String reason, HttpStatus httpStatus, ZonedDateTime timestamp, Boolean success) {
        this.reason = reason;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public Boolean getSuccess() {
        return success;
    }

}
