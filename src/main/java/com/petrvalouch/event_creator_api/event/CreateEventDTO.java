package com.petrvalouch.event_creator_api.event;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

/**
 * this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.label = label;
 */

public class CreateEventDTO {
    @NotBlank(message = "event name is required")
    private String eventName;

    //@NotBlank(message = "start date is required")
    private LocalDate startDate;

    //@NotBlank(message = "end date is required")
    private LocalDate endDate;

    @NotBlank(message = "location is required")
    private String location;

    @NotBlank(message = "label is required")
    private String label;

    public String getEventName() {
        return eventName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getLocation() {
        return location;
    }

    public String getLabel() {
        return label;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "CreateEventDTO [eventName=" + eventName + ", startDate=" + startDate + ", endDate=" + endDate
                + ", location=" + location + ", label=" + label + "]";
    }
}
