package com.anz.accounts.controller.exception;

public class AuthorisationException extends RuntimeException {
    public AuthorisationException(String message) {
        super(message);
    }
}
