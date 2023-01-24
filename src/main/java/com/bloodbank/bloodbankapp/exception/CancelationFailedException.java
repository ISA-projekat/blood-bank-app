package com.bloodbank.bloodbankapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CancelationFailedException extends RuntimeException {

    public CancelationFailedException(String message) {
        super(message);
    }
}
