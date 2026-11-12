package com.pedromolon.reimburseflow.infrastructure.web.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
