package com.petrvalouch.event_creator_api.event;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.petrvalouch.event_creator_api.common.exceptions.EventNotFoundException;

@Service
public class EventService {

    private EventRepository repo;
    private ModelMapper mapper;

    public EventService(EventRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public Event createEvent(CreateEventDTO data) {
        Event newEvent = mapper.map(data, Event.class);
        return this.repo.save(newEvent);
    }

    public List<Event> getAll() {
        return this.repo.findAll();
    }

    public Optional<Event> getById(Long id) {
        return this.repo.findById(id);
    }

    public boolean deleteById(Long id) throws EventNotFoundException {
        Optional<Event> result = this.getById(id);
        if (result.isEmpty()) {
            return false;
        }
        this.repo.delete(result.get());
        return true;
    }

    public Optional<Event> updateById(Long id, UpdateEventDTO data) {
        Optional<Event> result = this.getById(id);
        if(result.isEmpty()) {
            return result;
        }
        Event foundEvent = result.get();
        mapper.map(data, foundEvent);
        this.repo.save(foundEvent);
        return Optional.of(foundEvent);
    }
}
