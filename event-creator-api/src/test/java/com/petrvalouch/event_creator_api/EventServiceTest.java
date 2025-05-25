package com.petrvalouch.event_creator_api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.petrvalouch.event_creator_api.event.CreateEventDTO;
import com.petrvalouch.event_creator_api.event.Event;
import com.petrvalouch.event_creator_api.event.EventRepository;
import com.petrvalouch.event_creator_api.event.EventService;
import com.petrvalouch.event_creator_api.event.UpdateEventDTO;

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
        event1.setStartDate(LocalDate.of(2025, 5, 10));
        event1.setEndDate(LocalDate.of(2025, 5, 10));
        event1.setLocation("Zoom");
        event1.setLabel("School");
        this.events.add(event1);
        Event event2 = new Event();
        event2.setId(2L);
        event2.setEventName("Event Two");
        event2.setStartDate(LocalDate.of(2025, 5, 20));
        event2.setEndDate(LocalDate.of(2025, 5, 20));
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
        // Arrange
        Long eventId = 1L;
        Event expectedEvent = events.get(0);
        when(eventRepo.findById(eventId)).thenReturn(Optional.of(expectedEvent));

        // Act
        Optional<Event> result = eventService.getById(eventId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedEvent, result.get());
        verify(eventRepo, times(1)).findById(eventId);
    }

    @Test
    void getEventByIdShouldReturnEmptyWhenNotExists() {
        // Arrange
        Long nonExistentEventId = 99L;
        when(eventRepo.findById(nonExistentEventId)).thenReturn(Optional.empty());

        // Act
        Optional<Event> result = eventService.getById(nonExistentEventId);

        // Assert
        assertFalse(result.isPresent());
        verify(eventRepo, times(1)).findById(nonExistentEventId);
    }

    @Test
    void updateEventByIdShouldReturnUpdatedEventSuccessfully() {
        // Arrange
        Long eventId = 1L;
        Event existingEvent = events.get(0);
        
        UpdateEventDTO updateEventDTO = new UpdateEventDTO();
        updateEventDTO.setEventName("updated event");
        updateEventDTO.setStartDate(LocalDate.of(2025, 5, 15));
        updateEventDTO.setEndDate(LocalDate.of(2025, 5, 15));
        updateEventDTO.setLocation("New Location");
        updateEventDTO.setLabel("New Label");

        // Mock repository
        when(eventRepo.findById(eventId)).thenReturn(Optional.of(existingEvent));
        
        // Mock the mapper 
        doAnswer(request -> {
            UpdateEventDTO source = request.getArgument(0);
            Event destination = request.getArgument(1);
            
            destination.setEventName(source.getEventName());
            destination.setStartDate(source.getStartDate());
            destination.setEndDate(source.getEndDate());
            destination.setLocation(source.getLocation());
            destination.setLabel(source.getLabel());
            return null;
        }).when(modelMapper).map(any(UpdateEventDTO.class), any(Event.class));
        
        when(eventRepo.save(any(Event.class))).thenAnswer(request -> request.getArgument(0));

        // Act
        Optional<Event> result = eventService.updateById(eventId, updateEventDTO);

        // Assert
        assertTrue(result.isPresent());
        Event updatedEvent = result.get();
        
        // Verify
        assertEquals("updated event", updatedEvent.getEventName());
        assertEquals(LocalDate.of(2025, 5, 15), updatedEvent.getStartDate());
        assertEquals(LocalDate.of(2025, 5, 15), updatedEvent.getEndDate());
        assertEquals("New Location", updatedEvent.getLocation());
        assertEquals("New Label", updatedEvent.getLabel());
        
        // Verify interactions
        verify(eventRepo, times(1)).findById(eventId);
        verify(modelMapper, times(1)).map(any(UpdateEventDTO.class), any(Event.class));
        verify(eventRepo, times(1)).save(any(Event.class));
    }
}