package com.todo.backend.dto.response;

import lombok.Getter;

@Getter
public class ErrorResponse {

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
        private int status;
    private String message;
    
}
