package com.petrvalouch.event_creator_api.common;

import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.petrvalouch.event_creator_api.common.exceptions.EventNotFoundException;
import com.petrvalouch.event_creator_api.common.exceptions.ServiceValidationException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<String> handleEventNotFoundException(EventNotFoundException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceValidationException.class)
    public ResponseEntity<ValidationErrors> handleServiceValidationException(ServiceValidationException ex) {
        return new ResponseEntity<ValidationErrors>(ex.getErrors(), HttpStatus.BAD_REQUEST);
    }
}
