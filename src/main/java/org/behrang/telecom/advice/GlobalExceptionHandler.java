package org.behrang.telecom.advice;

import org.behrang.telecom.exception.PhoneNumberAlreadyActivatedException;
import org.behrang.telecom.exception.EntityNotFoundException;
import org.behrang.telecom.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleDataAccessExceptions(final EntityNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(PhoneNumberAlreadyActivatedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleDataAccessExceptions(final PhoneNumberAlreadyActivatedException ex) {
        return new ErrorResponse(ex.getMessage());
    }

}
