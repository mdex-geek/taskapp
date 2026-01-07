package com.todo.backend.exception.impl;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String message) {
            super(message);
    }

    
}
