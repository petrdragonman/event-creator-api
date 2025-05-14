package com.petrvalouch.event_creator_api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.petrvalouch.event_creator_api.event.CreateEventDTO;
import com.petrvalouch.event_creator_api.event.Event;
import com.petrvalouch.event_creator_api.event.EventRepository;
import com.petrvalouch.event_creator_api.event.EventService;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private EventRepository eventRepo;

    @InjectMocks
    private EventService eventService;

    List<Event> events;

    @BeforeEach
    void setUp() {
        this.events = new ArrayList<>();
        Event event1 = new Event();
        event1.setId(1L);
        event1.setEventName("Event One");
        event1.setStartDate(LocalDate.of(2025, 05, 10));
        event1.setEndDate(LocalDate.of(2025, 05, 10));
        event1.setLocation("Zoom");
        event1.setLabel("School");
        this.events.add(event1);
        Event event2 = new Event();
        event2.setId(1L);
        event2.setEventName("Event Two");
        event2.setStartDate(LocalDate.of(2025, 05, 20));
        event2.setEndDate(LocalDate.of(2025, 05, 20));
        event2.setLocation("Crows Nest");
        event2.setLabel("Work");
        this.events.add(event2);
    }

    @Test
    void createEventShouldAddEventSuccessfully() {
        // Arrange
        CreateEventDTO createEventDTO = new CreateEventDTO();
        
        Event expectedEvent = new Event();
        expectedEvent.setId(1L);
        expectedEvent.setStartDate(LocalDate.of(2025, 05, 15));
        expectedEvent.setEndDate(LocalDate.of(2025, 05, 15));
        expectedEvent.setEventName("Exam");
        expectedEvent.setLocation("Zoom");
        expectedEvent.setLabel("School");

        // Mock the mapping from DTO to Entity
        when(modelMapper.map(createEventDTO, Event.class)).thenReturn(expectedEvent);
        
        // Mock the repository save operation
        when(eventRepo.save(expectedEvent)).thenReturn(expectedEvent);

        // Act
        Event createdEvent = eventService.createEvent(createEventDTO);

        // Assert
        assertEquals(expectedEvent.getId(), createdEvent.getId());
        assertEquals(expectedEvent.getEventName(), createdEvent.getEventName());
        assertEquals(expectedEvent.getStartDate(), createdEvent.getStartDate());
        assertEquals(expectedEvent.getEndDate(), createdEvent.getEndDate());
        assertTrue(createdEvent.getLocation() == "Zoom");
        assertTrue(createdEvent.getLabel() == "School");
        verify(modelMapper, times(1)).map(createEventDTO, Event.class);
        verify(eventRepo, times(1)).save(expectedEvent);
    }

    @Test
    void getEventByIdShouldGetEventSuccessfully() {
        Event expectedEvent = new Event();
        when(eventRepo.getReferenceById(1L)).thenReturn(expectedEvent);
        assertTrue(expectedEvent.getId() == 1);
    }
}