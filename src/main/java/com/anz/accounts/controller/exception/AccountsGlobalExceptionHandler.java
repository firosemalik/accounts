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
        this.logException(exception, request, HttpStatus.BAD_REQUEST);
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
        this.logException(exception, request, HttpStatus.BAD_REQUEST);
        Status badRequest = Status.BAD_REQUEST;
        return new ResponseEntity<>(
                ApiError.builder()
                        .errorId(badRequest.getErrorId())
                        .message(badRequest.getMessage())
                        .detail(exception.getMessage())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                    HttpServletRequest request) {
        this.logException(exception, request, HttpStatus.NOT_FOUND);
        Status badRequest = Status.NOT_FOUND;
        return new ResponseEntity<>(
                ApiError.builder()
                        .errorId(badRequest.getErrorId())
                        .message(badRequest.getMessage())
                        .detail(exception.getMessage())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ApiError> handleValidationException(ValidationException exception,
                                                              HttpServletRequest request) {
        this.logException(exception, request, HttpStatus.BAD_REQUEST);
        if (exception.getCause() instanceof InvalidRequestException) {
            Status badRequest = Status.BAD_REQUEST;
            return new ResponseEntity<>(
                    ApiError.builder()
                            .errorId(badRequest.getErrorId())
                            .message(badRequest.getMessage())
                            .detail(exception.getCause().getMessage())
                            .build(), HttpStatus.BAD_REQUEST);
        } else if (exception.getCause() instanceof AuthorisationException) {
            Status badRequest = Status.UNAUTHORIZED;
            return new ResponseEntity<>(
                    ApiError.builder()
                            .errorId(badRequest.getErrorId())
                            .message(badRequest.getMessage())
                            .detail(exception.getCause().getMessage())
                            .build(), HttpStatus.UNAUTHORIZED);
        }
        return handleCatchAll(exception, request);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleCatchAll(Exception exception,
                                                   HttpServletRequest request) {
        this.logException(exception, request, HttpStatus.INTERNAL_SERVER_ERROR);
        Status badRequest = Status.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(
                ApiError.builder()
                        .errorId(badRequest.getErrorId())
                        .message(badRequest.getMessage())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logException(Throwable throwable, HttpServletRequest request, HttpStatus httpStatus) {
        //There can be security risk of logging request
        log.error("Exception in ms-accounts ".concat(httpStatus.toString()), throwable);
    }
}
