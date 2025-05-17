package com.petrvalouch.event_creator_api.common.exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
        System.out.println("not found exception called");
    }
}
