package com.petrvalouch.event_creator_api.event;

import java.time.LocalDate;

import com.petrvalouch.event_creator_api.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="events")
public class Event extends BaseEntity {
    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String label;

    public String getEventName() {
        return eventName;
    }

    public Event(String eventName, LocalDate startDate, LocalDate endDate, String location, String label) {
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.label = label;
    }

    public Event() {}

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
}
