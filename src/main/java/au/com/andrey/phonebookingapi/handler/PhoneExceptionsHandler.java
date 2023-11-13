package au.com.andrey.phonebookingapi.handler;

import au.com.andrey.phonebookingapi.exception.PhoneNotAvailableException;
import au.com.andrey.phonebookingapi.exception.PhoneNotFoundException;
import org.openapitools.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class PhoneExceptionsHandler {

    @ExceptionHandler(value = PhoneNotFoundException.class)
    private ResponseEntity<Error> handle(PhoneNotFoundException e) {
        Error error = new Error().message(e.getMessage()).status(HttpStatus.NOT_FOUND.value()).timestamp(OffsetDateTime.now());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(value = PhoneNotAvailableException.class)
    private ResponseEntity<Error> handle(PhoneNotAvailableException e) {
        Error error = new Error().message(e.getMessage()).status(HttpStatus.UNAUTHORIZED.value()).timestamp(OffsetDateTime.now());
        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
