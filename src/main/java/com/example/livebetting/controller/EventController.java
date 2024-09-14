package com.example.livebetting.controller;

import com.example.livebetting.data.entity.Event;
import com.example.livebetting.data.model.request.AddEventRequestModel;
import com.example.livebetting.mapper.EventMapper;
import com.example.livebetting.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/event")
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addEvent(@RequestBody AddEventRequestModel addEventRequestModel) {
        Event event = eventMapper.toEvent(addEventRequestModel);
        this.eventService.addEvent(event);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
