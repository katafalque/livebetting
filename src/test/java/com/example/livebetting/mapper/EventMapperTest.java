package com.example.livebetting.mapper;

import com.example.livebetting.data.entity.Event;
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
    }
}
