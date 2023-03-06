package com.anz.accounts.unit.controller.validator;

import com.anz.accounts.api.MandatoryHeaders;
import com.anz.accounts.controller.exception.AuthorisationException;
import com.anz.accounts.controller.exception.InvalidRequestException;
import com.anz.accounts.controller.validator.RequestHeaderValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class RequestHeaderValidatorTest {

    @InjectMocks
    private RequestHeaderValidator headerValidator;

    @Test
    void isValid_withValidHeaders_success() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(MandatoryHeaders.TRACE_ID, "123");
        httpHeaders.add(MandatoryHeaders.ACCESS_TOKEN, "100");
        assertTrue(headerValidator.isValid(httpHeaders, null));
    }

    @Test
    void isValid_withNullHeaders_error() {
        assertThatThrownBy(() -> headerValidator.isValid(null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void isValid_withoutTraceId_error() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(MandatoryHeaders.ACCESS_TOKEN, "123");
        assertThatThrownBy(() -> headerValidator.isValid(httpHeaders, null))
                .isInstanceOf(InvalidRequestException.class);
    }

    @Test
    void isValid_withEmptyTraceId_error() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(MandatoryHeaders.TRACE_ID, "");
        httpHeaders.add(MandatoryHeaders.ACCESS_TOKEN, "123");
        assertThatThrownBy(() -> headerValidator.isValid(httpHeaders, null))
                .isInstanceOf(InvalidRequestException.class);
    }

    @Test
    void isValid_withNullTraceId_error() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(MandatoryHeaders.TRACE_ID, null);
        httpHeaders.add(MandatoryHeaders.ACCESS_TOKEN, "123");
        assertThatThrownBy(() -> headerValidator.isValid(httpHeaders, null))
                .isInstanceOf(InvalidRequestException.class);
    }

    @Test
    void isValid_withoutAccessToken_error() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(MandatoryHeaders.TRACE_ID, "123");
        assertThatThrownBy(() -> headerValidator.isValid(httpHeaders, null))
                .isInstanceOf(AuthorisationException.class);
    }

    @Test
    void isValid_withEmptyAccessToken_error() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(MandatoryHeaders.TRACE_ID, "123");
        httpHeaders.add(MandatoryHeaders.ACCESS_TOKEN, "");
        assertThatThrownBy(() -> headerValidator.isValid(httpHeaders, null))
                .isInstanceOf(AuthorisationException.class);
    }

    @Test
    void isValid_withNullAccessToken_error() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(MandatoryHeaders.TRACE_ID, "123");
        httpHeaders.add(MandatoryHeaders.ACCESS_TOKEN, null);
        assertThatThrownBy(() -> headerValidator.isValid(httpHeaders, null))
                .isInstanceOf(AuthorisationException.class);
    }
}
