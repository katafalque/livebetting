package com.example.livebetting.controller;

import com.example.livebetting.data.model.response.GetBulletinResponseModel;
import com.example.livebetting.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bulletin")
public class BulletinController {
    private final EventService eventService;

    @Autowired
    public BulletinController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<GetBulletinResponseModel> getBulletin() {
        var events = eventService.getBulletin();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}
