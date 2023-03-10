package com.anz.accounts.controller.exception;

import com.anz.accounts.api.ApiError;
import com.anz.accounts.api.Status;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

@RestControllerAdvice
@NoArgsConstructor
@Slf4j
public class AccountsGlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception,
                                                                     HttpServletRequest request) {
        this.logException(exception, request, HttpStatus.BAD_REQUEST, false);
        Status badRequest = Status.BAD_REQUEST;
        return new ResponseEntity<>(
                ApiError.builder()
                        .errorId(badRequest.getErrorId())
                        .message(badRequest.getMessage())
                        .detail("Argument type mismatch")
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException exception,
                                                                   HttpServletRequest request) {
        this.logException(exception, request, HttpStatus.BAD_REQUEST, false);
        Status badRequest = Status.BAD_REQUEST;
        return new ResponseEntity<>(buildError(badRequest, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                    HttpServletRequest request) {
        this.logException(exception, request, HttpStatus.NOT_FOUND, false);
        Status badRequest = Status.NOT_FOUND;
        return new ResponseEntity<>(buildError(badRequest, exception.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({InvalidRequestException.class})
    public ResponseEntity<ApiError> handleInvalidRequestException(InvalidRequestException exception,
                                                                  HttpServletRequest request) {
        this.logException(exception, request, HttpStatus.BAD_REQUEST, false);
        Status badRequest = Status.BAD_REQUEST;
        return new ResponseEntity<>(
                buildError(badRequest, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthorisationException.class})
    public ResponseEntity<ApiError> handleAuthorisationException(AuthorisationException exception,
                                                                 HttpServletRequest request) {
        this.logException(exception, request, HttpStatus.UNAUTHORIZED, false);
        Status badRequest = Status.UNAUTHORIZED;
        return new ResponseEntity<>(
                buildError(badRequest, exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ApiError> handleValidationException(ValidationException exception,
                                                              HttpServletRequest request) {
        if (exception.getCause() instanceof InvalidRequestException) {
            this.logException(exception, request, HttpStatus.BAD_REQUEST, false);
            Status badRequest = Status.BAD_REQUEST;
            return new ResponseEntity<>(buildError(badRequest, exception.getCause().getMessage()), HttpStatus.BAD_REQUEST);
        } else if (exception.getCause() instanceof AuthorisationException) {
            this.logException(exception, request, HttpStatus.UNAUTHORIZED, false);
            Status badRequest = Status.UNAUTHORIZED;
            return new ResponseEntity<>(buildError(badRequest, exception.getCause().getMessage()), HttpStatus.UNAUTHORIZED);
        }
        return handleCatchAll(exception, request);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleCatchAll(Exception exception,
                                                   HttpServletRequest request) {
        this.logException(exception, request, HttpStatus.INTERNAL_SERVER_ERROR, true);
        Status badRequest = Status.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(
                ApiError.builder()
                        .errorId(badRequest.getErrorId())
                        .message(badRequest.getMessage())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ApiError buildError(Status badRequest, String message) {
        return ApiError.builder()
                .errorId(badRequest.getErrorId())
                .message(badRequest.getMessage())
                .detail(message)
                .build();
    }


    private void logException(Throwable throwable, HttpServletRequest request, HttpStatus httpStatus, boolean error) {
        //There can be security risk of logging request
        if (error) {
            log.error("Exception in ms-accounts ".concat(httpStatus.toString()), throwable);
        } else {
            log.warn("Exception in ms-accounts ".concat(httpStatus.toString()), throwable);
        }

    }
}
