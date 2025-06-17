package com.petrvalouch.event_creator_api.event;

import org.hamcrest.Matchers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrvalouch.event_creator_api.common.exceptions.EventNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(EventController.class)
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    EventService eventService;

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
    void tearDown() {}

    @Test
    void testGetById_success() throws Exception {
        // given
        given(this.eventService.getById(1L)).willReturn(Optional.of(events.get(0)));
        // when and then
        this.mockMvc.perform(get("/events/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eventName").value("Training"))
                .andExpect(jsonPath("$.startDate").value("2025-06-17"))
                .andExpect(jsonPath("$.endDate").value("2025-06-17"))
                .andExpect(jsonPath("$.location").value("Bank Street"))
                .andExpect(jsonPath("$.label").value("Dragonboat"));
        verify(this.eventService, times(1)).getById(1L);
    }

    @Test
    void testGetById_EventNotFound() throws Exception {
        // given
        given(eventService.getById(999L)).willReturn(Optional.empty());
        // when and then
        mockMvc.perform(get("/events/999"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Could not find event id: 999"));
        verify(this.eventService, times(1)).getById(999L);
    }

    @Test
    void testGetAllEvents() throws Exception {
        // given
        given(eventService.getAll()).willReturn(events);
        // when and then
        mockMvc.perform(get("/events").accept(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", Matchers.hasSize(2)))
            .andExpect(jsonPath("$[0].eventName").value("Training"))
            .andExpect(jsonPath("$[0].startDate").value("2025-06-17"))
            .andExpect(jsonPath("$[0].endDate").value("2025-06-17"))
            .andExpect(jsonPath("$[0].location").value("Bank Street"))
            .andExpect(jsonPath("$[0].label").value("Dragonboat"))
            .andExpect(jsonPath("$[1].eventName").value("Homework"));
        verify(this.eventService, times(1)).getAll();
    }

    @Test
    void testCreateEvent_success() throws Exception {
        // given
        CreateEventDTO createEventDTO = new CreateEventDTO();
        createEventDTO.setEventName("Set-up Pat Brunton");
        createEventDTO.setStartDate(LocalDate.of(2025, 6, 18));
        createEventDTO.setEndDate(LocalDate.of(2025, 6, 18));
        createEventDTO.setLocation("Crows Nest");
        createEventDTO.setLabel("Work");
        String json = this.objectMapper.writeValueAsString(createEventDTO);

        Event newEvent = new Event();
        newEvent.setId(3L);
        newEvent.setEventName("Set-up Pat Brunton");
        newEvent.setStartDate(LocalDate.of(2025, 6, 18));
        newEvent.setEndDate(LocalDate.of(2025, 6, 18));
        newEvent.setLocation("Crows Nest");
        newEvent.setLabel("Work");

        given(eventService.createEvent(Mockito.any(CreateEventDTO.class))).willReturn(newEvent);
        // when and then
        mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.eventName").value("Set-up Pat Brunton"))
            .andExpect(jsonPath("$.startDate").value("2025-06-18"))
            .andExpect(jsonPath("$.endDate").value("2025-06-18"))
            .andExpect(jsonPath("$.location").value("Crows Nest"))
            .andExpect(jsonPath("$.label").value("Work"));
        verify(eventService, times(1)).createEvent(Mockito.any(CreateEventDTO.class));
    }

    @Test
    void testDeleteById_success() throws Exception {
        // given
        given(eventService.deleteById(1L)).willReturn(true);
        // when and then
        mockMvc.perform(delete("/events/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
        verify(eventService, times(1)).deleteById(eq(1L));
    }

    @Test
    void testDeleteById_nonExistingId() throws Exception {
        // given
        doThrow(new EventNotFoundException(999L)).when(eventService).deleteById(999L);
        // when and then
        mockMvc.perform(delete("/events/999").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
        verify(eventService, times(1)).deleteById(eq(999L));
    }

    @Test
    void testUpdateById_success() throws Exception {
        // given
        UpdateEventDTO updateEventDTO = new UpdateEventDTO();
        updateEventDTO.setEventName("Set-up Johnson Hall");
        updateEventDTO.setStartDate(LocalDate.of(2025, 6, 18));
        updateEventDTO.setEndDate(LocalDate.of(2025, 6, 18));
        updateEventDTO.setLocation("Crows Nest");
        updateEventDTO.setLabel("Work");
        String json = this.objectMapper.writeValueAsString(updateEventDTO);

        Event updatedEvent = new Event();
        updatedEvent.setId(3L);
        updatedEvent.setEventName("Set-up Johnson Hall");
        updatedEvent.setStartDate(LocalDate.of(2025, 6, 18));
        updatedEvent.setEndDate(LocalDate.of(2025, 6, 18));
        updatedEvent.setLocation("Crows Nest");
        updatedEvent.setLabel("Work");

        given(eventService.updateById(eq(3L), Mockito.any(UpdateEventDTO.class))).willReturn(Optional.of(updatedEvent));
        // when and then
        mockMvc.perform(patch("/events/3").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.eventName").value("Set-up Johnson Hall"))
            .andExpect(jsonPath("$.startDate").value("2025-06-18"))
            .andExpect(jsonPath("$.endDate").value("2025-06-18"))
            .andExpect(jsonPath("$.location").value("Crows Nest"))
            .andExpect(jsonPath("$.label").value("Work"));
        verify(eventService, times(1)).updateById(eq(3L), Mockito.any(UpdateEventDTO.class));
    }

    @Test
    void testUpdateById_NonExistentId() throws Exception {
        // given
        UpdateEventDTO updateEventDTO = new UpdateEventDTO();
        updateEventDTO.setEventName("Set-up Johnson Hall");
        updateEventDTO.setStartDate(LocalDate.of(2025, 6, 18));
        updateEventDTO.setEndDate(LocalDate.of(2025, 6, 18));
        updateEventDTO.setLocation("Crows Nest");
        updateEventDTO.setLabel("Work");
        String json = this.objectMapper.writeValueAsString(updateEventDTO);
        given(eventService.updateById(eq(999L), Mockito.any(UpdateEventDTO.class))).willThrow(new EventNotFoundException(999L));
        // when and then
        mockMvc.perform(patch("/events/999").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$").value("Could not find event id: 999"));
    }
}
