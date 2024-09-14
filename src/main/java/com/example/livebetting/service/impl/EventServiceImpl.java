package com.example.livebetting.service.impl;

import com.example.livebetting.data.entity.Event;
import com.example.livebetting.data.entity.Market;
import com.example.livebetting.data.model.dto.GeneratedOddsDto;
import com.example.livebetting.data.repository.EventRepository;
import com.example.livebetting.service.EventService;
import com.example.livebetting.utils.OddGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@EnableScheduling
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

    @Override
    @Scheduled(fixedRate = 1000)
    public void addMarketsToEvents() {
        Set<UUID> events = this.eventRepository.findAllIds();
        if (!events.isEmpty()) {
            for (UUID eventId : events) {
                Optional<Event> optionalEvent = this.eventRepository.findById(eventId);
                if (optionalEvent.isPresent()) {
                    Event event = optionalEvent.get();
                    GeneratedOddsDto newOdds = OddGenerator.generateOdds();
                    Market market = Market.builder()
                            .homeTeam(newOdds.getHomeTeamOdd())
                            .draw(newOdds.getDrawOdd())
                            .awayTeam(newOdds.getAwayTeamOdd())
                            .createdAt(OffsetDateTime.now())
                            .build();

                    event.addMarket(market);
                    this.eventRepository.save(event);
                }
            }
        }
    }
}
