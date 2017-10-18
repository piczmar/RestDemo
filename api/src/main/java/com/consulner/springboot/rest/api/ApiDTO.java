package com.consulner.springboot.rest.api;

public class ApiDTO {

    private final String message;

    public ApiDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
