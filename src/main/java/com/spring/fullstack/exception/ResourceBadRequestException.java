package com.spring.fullstack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ResourceBadRequestException extends RuntimeException{
    public ResourceBadRequestException(String message) {
        super(message);
    }
}
