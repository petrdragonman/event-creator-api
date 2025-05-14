package com.petrvalouch.event_creator_api.common.exceptions;

import org.springframework.boot.context.properties.bind.validation.ValidationErrors;

public class ServiceValidationException extends Exception {
    private ValidationErrors errors;

    public ServiceValidationException(ValidationErrors errors) {
        this.errors = errors;
    }

    public ValidationErrors getErrors() {
        return errors;
    }
}
