package com.example.livebetting.service;

import com.example.livebetting.data.entity.Event;
import com.example.livebetting.data.entity.Market;
import com.example.livebetting.data.model.dto.EventMarketDto;
import com.example.livebetting.data.repository.EventRepository;
import com.example.livebetting.mapper.BulletinMapper;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventServiceTest {
    private static Faker faker;

    @Mock
    private EventRepository eventRepository;
    @Mock
    private BulletinMapper bulletinMapper;
    private EventServiceImpl eventService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
        eventService = new EventServiceImpl(eventRepository, bulletinMapper);
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

        var event = Event.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .league(league)
                .startTime(startTime)
                .markets(new LinkedHashSet<>())
                .build();

        event.addMarket(market);

        /* Act */
        eventService.addEvent(event);

        /* Assert */
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void should_add_markets_to_events() {
        /* Arrange */
        Event event = Event.builder().id(UUID.randomUUID()).markets(new LinkedHashSet<>()).build();
        Market market = Market.builder().id(UUID.randomUUID()).build();
        event.addMarket(market);

        Set<UUID> eventIds = new HashSet<>();
        eventIds.add(event.getId());


        when(eventRepository.findAllIds()).thenReturn(eventIds);
        when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));

        /* Act */
        eventService.addMarketsToEvents();

        /* Assert */
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void should_get_bulletin() {
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

        var event = Event.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .league(league)
                .markets(new LinkedHashSet<>())
                .startTime(startTime)
                .build();

        event.addMarket(market);
        Set<Event> events = new LinkedHashSet<>();
        events.add(event);

        when(eventRepository.findByStartTimeAfter(any())).thenReturn(events);

        var result = eventService.getBulletin();

        verify(eventRepository, times(1)).findByStartTimeAfter(any());
        assertThat(result.getBulletin()).hasSize(events.size());
        EventMarketDto eventMarketDto = result.getBulletin().iterator().next();
        assertThat(eventMarketDto).isNotNull();
        assertThat(eventMarketDto.getMarkets()).hasSize(event.getMarkets().size());
        assertThat(eventMarketDto.getEventName()).isEqualTo(event.getHomeTeam() + " - " + event.getAwayTeam());
    }
}
