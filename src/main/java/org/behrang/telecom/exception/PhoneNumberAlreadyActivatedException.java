package org.behrang.telecom.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Phone number already activated")
public class PhoneNumberAlreadyActivatedException extends DataAccessException {
    public PhoneNumberAlreadyActivatedException(String msg) {
        super(msg);
    }

    public PhoneNumberAlreadyActivatedException() {
        this("");
    }
}
