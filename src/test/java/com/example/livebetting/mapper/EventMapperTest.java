package com.example.livebetting.mapper;

import com.example.livebetting.data.entity.Event;
import com.example.livebetting.data.entity.Market;
import com.example.livebetting.data.model.request.AddEventRequestModel;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EventMapperTest {
    private static Faker faker;
    private final EventMapper eventMapper = Mappers.getMapper(EventMapper.class);

    @BeforeEach
    void setup() {
        faker = new Faker();
    }

    @Test
    void should_map_market() {
        /* Arrange */
        AddEventRequestModel requestModel = AddEventRequestModel.builder()
                .homeTeamOdd(faker.random().nextDouble())
                .drawOdd(faker.random().nextDouble())
                .awayTeamOdd(faker.random().nextDouble())
                .build();

        /* Act */
        Market market = eventMapper.toMarket(requestModel);

        /* Assert */
        assertThat(market).isNotNull();
        assertThat(market.getHomeTeam()).isEqualTo(requestModel.getHomeTeamOdd());
        assertThat(market.getAwayTeam()).isEqualTo(requestModel.getAwayTeamOdd());
        assertThat(market.getDraw()).isEqualTo(requestModel.getDrawOdd());
        assertThat(market.getCreatedAt()).isNotNull();
    }

    @Test
    void should_map_event() {
        /* Arrange */
        AddEventRequestModel requestModel = AddEventRequestModel.builder()
                .league(faker.esports().league())
                .homeTeam(faker.esports().team())
                .awayTeam(faker.esports().team())
                .startTime(OffsetDateTime.now())
                .build();

        /* Act */
        Event event = eventMapper.toEvent(requestModel);

        /* Assert */
        assertThat(event).isNotNull();
        assertThat(event.getLeague()).isEqualTo(requestModel.getLeague());
        assertThat(event.getHomeTeam()).isEqualTo(requestModel.getHomeTeam());
        assertThat(event.getAwayTeam()).isEqualTo(requestModel.getAwayTeam());
        assertThat(event.getStartTime()).isEqualTo(requestModel.getStartTime());

        Market market = event.getMarket();
        assertThat(market).isNotNull();
        assertThat(market.getHomeTeam()).isEqualTo(0.0);
        assertThat(market.getAwayTeam()).isEqualTo(0.0);
        assertThat(market.getDraw()).isEqualTo(0.0);
        assertThat(market.getCreatedAt()).isNotNull();
    }

    @Test
    void should_map_market_with_bidirectional_relationship() {
        /* Arrange */
        AddEventRequestModel requestModel = AddEventRequestModel.builder()
                .homeTeamOdd(faker.random().nextDouble())
                .drawOdd(faker.random().nextDouble())
                .awayTeamOdd(faker.random().nextDouble())
                .build();

        /* Act */
        Market market = eventMapper.createMarketWithBidirectionalRelationship(requestModel);

        /* Assert */
        assertThat(market).isNotNull();
        assertThat(market.getHomeTeam()).isEqualTo(requestModel.getHomeTeamOdd());
        assertThat(market.getAwayTeam()).isEqualTo(requestModel.getAwayTeamOdd());
        assertThat(market.getDraw()).isEqualTo(requestModel.getDrawOdd());
        assertThat(market.getCreatedAt()).isNotNull();

        Event event = market.getEvent();
        assertThat(event).isNotNull();
    }

    @Test
    void should_map_event_with_bidirectional_relationship() {
        /* Arrange */
        AddEventRequestModel requestModel = AddEventRequestModel.builder()
                .league(faker.esports().league())
                .homeTeam(faker.esports().team())
                .awayTeam(faker.esports().team())
                .startTime(OffsetDateTime.now())
                .build();

        /* Act */
        Event event = eventMapper.createEventWithBidirectionalRelationship(requestModel);

        /* Assert */
        assertThat(event).isNotNull();
        assertThat(event.getLeague()).isEqualTo(requestModel.getLeague());
        assertThat(event.getHomeTeam()).isEqualTo(requestModel.getHomeTeam());
        assertThat(event.getAwayTeam()).isEqualTo(requestModel.getAwayTeam());
        assertThat(event.getStartTime()).isEqualTo(requestModel.getStartTime());

        Market market = event.getMarket();
        assertThat(market).isNotNull();
        assertThat(market.getHomeTeam()).isEqualTo(0.0);
        assertThat(market.getAwayTeam()).isEqualTo(0.0);
        assertThat(market.getDraw()).isEqualTo(0.0);
        assertThat(market.getCreatedAt()).isNotNull();
        
        assertThat(market.getEvent()).isEqualTo(event);
    }
}
