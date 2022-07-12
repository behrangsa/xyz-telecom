package org.behrang.telecom.exception;

import org.springframework.dao.DataAccessException;

public class PhoneNumberAlreadyActivatedException extends DataAccessException {
    public PhoneNumberAlreadyActivatedException(String msg) {
        super(msg);
    }

}
