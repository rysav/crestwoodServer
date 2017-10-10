package com.crestwood.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ryan on 10/10/17.
 */
@ResponseStatus(value= HttpStatus.CONFLICT)
public class AlreadyExistsException extends Exception {

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(Throwable throwable) {
        super(throwable);
    }

    public AlreadyExistsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
