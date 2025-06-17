package com.petrvalouch.event_creator_api.event;

import java.time.LocalDate;

public class UpdateEventDTO {
    private String eventName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String location;

    private String label;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "UpdateEventDTO [eventName=" + eventName + ", startDate=" + startDate + ", endDate=" + endDate
                + ", location=" + location + ", label=" + label + "]";
    }

    
}
