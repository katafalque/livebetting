package com.example.livebetting.service;

import com.example.livebetting.data.entity.Event;

public interface EventService {
    void addEvent(Event event);

    void addMarketsToEvents();
}
