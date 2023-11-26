package com.kishan.productservice.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class SomethingWentWrongException extends Exception {
    public SomethingWentWrongException(String msg) {
        super(msg);
    }
}
