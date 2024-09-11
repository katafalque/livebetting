package com.example.livebetting.service.impl;

import com.example.livebetting.data.entity.Event;
import com.example.livebetting.data.repository.EventRepository;
import com.example.livebetting.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void addEvent(Event event) {
        this.eventRepository.save(event);
    }
}
