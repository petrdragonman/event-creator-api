package com.petrvalouch.event_creator_api.event;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petrvalouch.event_creator_api.common.exceptions.EventNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventController {

    private EventService eventService;

    EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody @Valid CreateEventDTO data) {
        Event newEvent = this.eventService.createEvent(data);
        return new ResponseEntity<Event>(newEvent, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = this.eventService.getAll();
        return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable Long id) throws EventNotFoundException {
        Optional<Event> result = this.eventService.getById(id);
        Event foundEvent = result.orElseThrow(() -> new EventNotFoundException(id));
        return new ResponseEntity<>(foundEvent, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Event> updateById(@PathVariable Long id, @Valid @RequestBody UpdateEventDTO data) {
        Optional<Event> result = this.eventService.updateById(id, data);
        Event foundEvent = result.orElseThrow(() -> new EventNotFoundException(id));
        return new ResponseEntity<>(foundEvent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws Exception {
        boolean wasDeleted = this.eventService.deleteById(id);
        if(!wasDeleted) {
            throw new EventNotFoundException(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
