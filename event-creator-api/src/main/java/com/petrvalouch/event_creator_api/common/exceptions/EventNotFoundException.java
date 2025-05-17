package com.petrvalouch.event_creator_api.common.exceptions;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(Long id) {
        super("Could not find event id: " + id);
    }
}
