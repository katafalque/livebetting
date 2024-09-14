package com.example.livebetting.service;

import com.example.livebetting.data.entity.Bulletin;
import com.example.livebetting.data.entity.BulletinEvent;
import com.example.livebetting.data.entity.Event;
import com.example.livebetting.data.entity.Market;
import com.example.livebetting.data.repository.EventRepository;
import com.example.livebetting.service.impl.EventServiceImpl;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventServiceTest {
    private static Faker faker;

    @Mock
    private EventRepository eventRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    void should_save_event() {
        /* Arrange */
        var homeTeam = faker.esports().team();
        var awayTeam = faker.esports().team();
        var league = faker.esports().league();
        var startTime = faker.date().future(2, TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC);
        var awayTeamOdd = faker.random().nextDouble();
        var homeTeamOdd = faker.random().nextDouble();
        var drawOdd = faker.random().nextDouble();

        var market = Market.builder()
                .awayTeam(awayTeamOdd)
                .homeTeam(homeTeamOdd)
                .draw(drawOdd)
                .createdAt(OffsetDateTime.now())
                .build();

        var bulletin = Bulletin.builder()
                .date(new Date())
                .build();

        var bulletinEvent = BulletinEvent.builder()
                .bulletin(bulletin)
                .build();

        var event = Event.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .league(league)
                .startTime(startTime)
                .bulletinEvent(bulletinEvent)
                .build();

        event.addMarket(market);

        var eventService = new EventServiceImpl(eventRepository);

        /* Act */
        eventService.addEvent(event);

        /* Assert */
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void should_add_markets_to_events() {
        /* Arrange */
        Event event = Event.builder().id(UUID.randomUUID()).markets(new ArrayList<>()).build();
        Market market = Market.builder().id(UUID.randomUUID()).build();
        event.addMarket(market);

        Set<UUID> eventIds = new HashSet<>();
        eventIds.add(event.getId());


        when(eventRepository.findAllIds()).thenReturn(eventIds);
        when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));

        /* Act */
        var eventService = new EventServiceImpl(eventRepository);
        eventService.addMarketsToEvents();

        /* Assert */
        verify(eventRepository, times(1)).save(event);
    }
}
