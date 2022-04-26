package com.passwordmanagementSystem.exception;

public class PasswordExistException extends RuntimeException {
    public PasswordExistException(String message) {
        super(message);
    }
}
