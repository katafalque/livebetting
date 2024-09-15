package com.example.livebetting.service;

import com.example.livebetting.data.entity.Event;
import com.example.livebetting.data.model.response.GetBulletinResponseModel;

public interface EventService {
    void addEvent(Event event);

    void addMarketsToEvents();

    GetBulletinResponseModel getBulletin();
}
