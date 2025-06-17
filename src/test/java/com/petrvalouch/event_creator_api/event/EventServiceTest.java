package com.petrvalouch.event_creator_api.event;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    EventRepository repo;

    @InjectMocks
    EventService eventService = new EventService(repo, new ModelMapper());

    List<Event> events;

    @BeforeEach
    void setUp() {
        this.events = new ArrayList<Event>();
        Event event1 = new Event();
        event1.setId(1L);
        event1.setEventName("Training");
        event1.setStartDate(LocalDate.of(2025, 6, 17));
        event1.setEndDate(LocalDate.of(2025, 6, 17));
        event1.setLocation("Bank Street");
        event1.setLabel("Dragonboat");
        events.add(event1);
        Event event2 = new Event();
        event2.setId(2L);
        event2.setEventName("Homework");
        event2.setStartDate(LocalDate.of(2025, 6, 18));
        event2.setEndDate(LocalDate.of(2025, 6, 18));
        event2.setLocation("home");
        event2.setLabel("School");
        events.add(event2);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void test_getById_success() {
        // Given
        Event event = new Event();
        event.setId(1L);
        event.setEventName("training");
        event.setStartDate(LocalDate.of(2025, 6, 16));
        event.setEndDate(LocalDate.of(2025, 6, 16));
        event.setLocation("Bank Street");
        event.setLabel("dragonboat");
        BDDMockito.given(repo.findById(1L)).willReturn(Optional.of(event));
        // When
        Optional<Event> returnedEvent = eventService.getById(1L);
        // Then
        Assertions.assertThat(returnedEvent.get().getId()).isEqualTo(event.getId());
        Assertions.assertThat(returnedEvent.get().getEventName()).isEqualTo(event.getEventName());
        Assertions.assertThat(returnedEvent.get().getStartDate()).isEqualTo(event.getStartDate());
        Assertions.assertThat(returnedEvent.get().getEndDate()).isEqualTo(event.getEndDate());
        Assertions.assertThat(returnedEvent.get().getLocation()).isEqualTo(event.getLocation());
        Assertions.assertThat(returnedEvent.get().getLabel()).isEqualTo(event.getLabel());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    void test_getById_notFound() {
        // given
        BDDMockito.given(repo.findById(999L)).willReturn(Optional.empty());
        // when
        Optional<Event> returnedEvent = eventService.getById(999L);
        // then
        Assertions.assertThat(returnedEvent).isEmpty();
        verify(repo, times(1)).findById(999L);
    }

    @Test
    void test_getAll_success() {
        // given
        given(repo.findAll()).willReturn(events);
        // when
        List<Event> returnedEvents = eventService.getAll();
        // then
        Assertions.assertThat(returnedEvents.size()).isEqualTo(events.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void test_createEvent_success() throws Exception {
        // given
        CreateEventDTO eventDTO = new CreateEventDTO();
        eventDTO.setEventName("call Xiaoya");
        eventDTO.setStartDate(LocalDate.of(2025, 6, 17));
        eventDTO.setEndDate(LocalDate.of(2025, 6, 17));
        eventDTO.setLocation("home");
        eventDTO.setLabel("Family");
        
        Event event = new Event();
        event.setId(1L);
        event.setEventName("call Xiaoya");
        event.setStartDate(LocalDate.of(2025, 6, 17));
        event.setEndDate(LocalDate.of(2025, 6, 17));
        event.setLocation("home");
        event.setLabel("Family");

        given(repo.save(BDDMockito.any(Event.class))).willReturn(event);

        // when
        Event createdEvent = eventService.createEvent(eventDTO);

        // then
        Assertions.assertThat(createdEvent.getId()).isEqualTo(1);
        Assertions.assertThat(createdEvent.getEventName()).isEqualTo("call Xiaoya");
        Assertions.assertThat(createdEvent.getStartDate()).isEqualTo(LocalDate.of(2025, 6, 17));
        Assertions.assertThat(createdEvent.getEndDate()).isEqualTo(LocalDate.of(2025, 6, 17));
        Assertions.assertThat(createdEvent.getLocation()).isEqualTo("home");
        Assertions.assertThat(createdEvent.getLabel()).isEqualTo("Family");
        verify(repo, times(1)).save(BDDMockito.any(Event.class));
    }

    @Test
    void test_updateById_success() {
        // given
        UpdateEventDTO eventDTO = new UpdateEventDTO();
        eventDTO.setEventName("call mum");
        eventDTO.setStartDate(LocalDate.of(2025, 6, 17));
        eventDTO.setEndDate(LocalDate.of(2025, 6, 17));
        eventDTO.setLocation("home");
        eventDTO.setLabel("Family");

        Event oldEvent = new Event();
        oldEvent.setId(1L);
        oldEvent.setEventName("call Xiaoya");
        oldEvent.setStartDate(LocalDate.of(2025, 6, 17));
        oldEvent.setEndDate(LocalDate.of(2025, 6, 17));
        oldEvent.setLocation("home");
        oldEvent.setLabel("Family");
        
        Event updateEvent = new Event();
        updateEvent.setId(1L);
        updateEvent.setEventName("call mum");
        updateEvent.setStartDate(LocalDate.of(2025, 6, 17));
        updateEvent.setEndDate(LocalDate.of(2025, 6, 17));
        updateEvent.setLocation("home");
        updateEvent.setLabel("Family");

        given(repo.findById(1L)).willReturn(Optional.of(oldEvent));
        given(repo.save(oldEvent)).willReturn(oldEvent);
        // when 
        Optional<Event> updatedEvent = eventService.updateById(1L, eventDTO);
        // then
        Assertions.assertThat(updatedEvent.get().getId()).isEqualTo(1L);
        Assertions.assertThat(updatedEvent.get().getEventName()).isEqualTo("call mum");
        verify(repo, times(1)).findById(1L);
        verify(repo, times(1)).save(oldEvent);
    }

    @Test
    void test_updateById_EventNotFound() {
        // given
        UpdateEventDTO updateEvent = new UpdateEventDTO();
        updateEvent.setEventName("call dad");
        updateEvent.setStartDate(LocalDate.of(2025, 6, 18));
        updateEvent.setEndDate(LocalDate.of(2025, 6, 18));
        updateEvent.setLocation("home");
        updateEvent.setLabel("Family");

        given(repo.findById(999L)).willReturn(Optional.empty());
        // when
        Optional<Event> returnedEvent = eventService.getById(999L);
        // then
        Assertions.assertThat(returnedEvent).isEmpty();
        verify(repo, times(1)).findById(999L);
    }

    @Test
    void test_deleteById_success() {
        // given
        Event event = new Event();
        event.setId(1L);
        event.setEventName("call Xiaoya");
        event.setStartDate(LocalDate.of(2025, 6, 17));
        event.setEndDate(LocalDate.of(2025, 6, 17));
        event.setLocation("home");
        event.setLabel("Family");

        given(repo.findById(1L)).willReturn(Optional.of(event));
        willDoNothing().given(repo).delete(event);
        // when
        boolean result = eventService.deleteById(1L);
        // then
        Assertions.assertThat(result).isTrue();
        verify(repo, times(1)).delete(event);
    }

    @Test
    void test_deleteById_notFound() {
        // given
        given(repo.findById(999L)).willReturn(Optional.empty());
        // when
        boolean result = eventService.deleteById(999L);
        // then
        Assertions.assertThat(result).isFalse();
        verify(repo, never()).delete(any());
    }

}