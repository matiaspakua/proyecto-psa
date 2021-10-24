package com.aninfo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String message) {
        super(message);
    }
}
