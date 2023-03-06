package com.anz.accounts.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Status {
    BAD_REQUEST("API-400","Bad Request", HttpStatus.BAD_REQUEST),
    NOT_FOUND("API-404","Not Found", HttpStatus.NOT_FOUND),
    UNAUTHORIZED("API-401","Unauthorized", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("API-403","Forbidden", HttpStatus.FORBIDDEN),

    INTERNAL_SERVER_ERROR("API-500","Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String errorId;
    private final String message;
    private final HttpStatus httpStatus;

}
