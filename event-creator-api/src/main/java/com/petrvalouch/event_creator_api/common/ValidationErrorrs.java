package com.petrvalouch.event_creator_api.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ValidationErrorrs {
    private HashMap<String, ArrayList<String>> errors;

    public ValidationErrorrs() {
        this.errors = new HashMap<>();
    }

    public boolean isEmpty() {
        return this.errors.isEmpty();
    }

    public void addError(String field, String message) {
        this.errors.computeIfAbsent(field, f -> new ArrayList<>()).add(message);
    }

    public Map<String, ArrayList<String>> getErrors() {
        return Collections.unmodifiableMap(this.errors);
    }
    
}
