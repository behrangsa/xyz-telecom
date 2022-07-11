package org.behrang.telecom.exception;

import org.springframework.dao.DataAccessException;

public class PhoneNumberNotFoundException extends DataAccessException {
    public PhoneNumberNotFoundException(String msg) {
        super(msg);
    }
}
