package com.anz.accounts.unit.controller.exception;

import com.anz.accounts.api.ApiError;
import com.anz.accounts.controller.exception.AccountsGlobalExceptionHandler;
import com.anz.accounts.controller.exception.AuthorisationException;
import com.anz.accounts.controller.exception.InvalidRequestException;
import com.anz.accounts.controller.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class AccountGlobalExceptionHandlerTest {

    @InjectMocks
    private AccountsGlobalExceptionHandler accountsGlobalExceptionHandler;

    @Mock
    private HttpServletRequest request;

    @Test
    void testHandleMethodArgumentTypeMismatch_shouldReturnError() {
        ResponseEntity<ApiError> error = accountsGlobalExceptionHandler
                .handleMethodArgumentTypeMismatch(new MethodArgumentTypeMismatchException("ABC", Long.class, "customerId", null, null), request);

        assertAll("response",
                () -> {
                    assertThat(error.getStatusCode(), is(HttpStatus.BAD_REQUEST));
                    assertNotNull(error.getBody());
                    ApiError body = error.getBody();
                    assertAll("body",
                            () -> assertThat(body.getErrorId(), is("API-400")),
                            () -> assertThat(body.getMessage(), is("Bad Request")),
                            () -> assertThat(body.getDetail(), is("Argument type mismatch")));

                });
    }

    @Test
    void testHandleResourceNotFoundException_shouldReturnError() {
        ResponseEntity<ApiError> error = accountsGlobalExceptionHandler
                .handleResourceNotFoundException(new ResourceNotFoundException("Accounts not found for customer id 123"), request);

        assertAll("response",
                () -> {
                    assertThat(error.getStatusCode(), is(HttpStatus.NOT_FOUND));
                    assertNotNull(error.getBody());
                    ApiError body = error.getBody();
                    assertAll("body",
                            () -> assertThat(body.getErrorId(), is("API-404")),
                            () -> assertThat(body.getMessage(), is("Not Found")),
                            () -> assertThat(body.getDetail(), is("Accounts not found for customer id 123")));

                });
    }

    @Test
    void testHandleInvalidRequestException_shouldReturnError() {
        ResponseEntity<ApiError> error = accountsGlobalExceptionHandler
                .handleValidationException(new ValidationException(new InvalidRequestException("Missing trace id header")), request);

        assertAll("response",
                () -> {
                    assertThat(error.getStatusCode(), is(HttpStatus.BAD_REQUEST));
                    assertNotNull(error.getBody());
                    ApiError body = error.getBody();
                    assertAll("body",
                            () -> assertThat(body.getErrorId(), is("API-400")),
                            () -> assertThat(body.getMessage(), is("Bad Request")),
                            () -> assertThat(body.getDetail(), is("Missing trace id header")));

                });
    }

    @Test
    void testHandleUnauthorisedException_shouldReturnError() {
        ResponseEntity<ApiError> error = accountsGlobalExceptionHandler
                .handleValidationException(new ValidationException(new AuthorisationException("Missing access token header")), request);

        assertAll("response",
                () -> {
                    assertThat(error.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
                    assertNotNull(error.getBody());
                    ApiError body = error.getBody();
                    assertAll("body",
                            () -> assertThat(body.getErrorId(), is("API-401")),
                            () -> assertThat(body.getMessage(), is("Unauthorized")),
                            () -> assertThat(body.getDetail(), is("Missing access token header")));

                });
    }

    @Test
    void testHandleCatchAll_shouldReturnError() {
        ResponseEntity<ApiError> error = accountsGlobalExceptionHandler
                .handleCatchAll(new Exception("Internal error"), request);

        assertAll("response",
                () -> {
                    assertThat(error.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
                    assertNotNull(error.getBody());
                    ApiError body = error.getBody();
                    assertAll("body",
                            () -> assertThat(body.getErrorId(), is("API-500")),
                            () -> assertThat(body.getMessage(), is("Internal server error")));

                });
    }
}
