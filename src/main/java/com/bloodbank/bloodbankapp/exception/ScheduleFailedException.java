package com.bloodbank.bloodbankapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ScheduleFailedException extends RuntimeException{

    public ScheduleFailedException(String message) { super(message); }
}
