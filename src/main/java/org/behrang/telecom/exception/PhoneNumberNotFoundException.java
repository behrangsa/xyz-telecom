package org.behrang.telecom.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Phone number not found")
public class PhoneNumberNotFoundException extends DataAccessException {

    public PhoneNumberNotFoundException(String msg) {
        super(msg);
    }

    public PhoneNumberNotFoundException() {
        this("");
    }
}
