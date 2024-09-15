package com.example.livebetting.mapper;

import com.example.livebetting.data.entity.Event;
import com.example.livebetting.data.entity.Market;
import com.example.livebetting.data.model.dto.EventMarketDto;
import com.example.livebetting.data.model.dto.MarketDto;
import com.example.livebetting.data.model.response.GetBulletinResponseModel;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BulletinMapperTest {

    private static Faker faker;
    private final BulletinMapper mapper = Mappers.getMapper(BulletinMapper.class);

    @BeforeEach
    void setup() {
        faker = new Faker();
    }

    @Test
    void should_map_event_to_event_market_dto() {
        /* Arrange */
        Event event = Event.builder()
                .homeTeam(faker.esports().team())
                .awayTeam(faker.esports().team())
                .startTime(OffsetDateTime.now())
                .markets(Stream.of(
                        Market.builder()
                                .homeTeam(faker.random().nextDouble())
                                .draw(faker.random().nextDouble())
                                .awayTeam(faker.random().nextDouble())
                                .createdAt(OffsetDateTime.now().minusDays(1))
                                .build(),
                        Market.builder()
                                .homeTeam(faker.random().nextDouble())
                                .draw(faker.random().nextDouble())
                                .awayTeam(faker.random().nextDouble())
                                .createdAt(OffsetDateTime.now())
                                .build()
                ).collect(Collectors.toSet()))
                .build();

        /* Act */
        EventMarketDto result = mapper.eventToEventMarketDto(event);

        /* Assert */
        assertThat(result).isNotNull();
        assertThat(result.getEventName()).isEqualTo(event.getHomeTeam() + " - " + event.getAwayTeam());
        assertThat(result.getStartTime()).isEqualTo(event.getStartTime());
        assertThat(result.getMarkets()).hasSize(event.getMarkets().size());
        assertThat(result.getMarkets().stream()
                .map(MarketDto::getCreatedAt)
                .sorted(Comparator.naturalOrder())
                .toList())
                .isEqualTo(result.getMarkets().stream()
                        .map(MarketDto::getCreatedAt)
                        .sorted(Comparator.naturalOrder())
                        .toList());
    }

    @Test
    void should_map_markets_to_sorted_market_dtos() {
        /* Arrange */
        Set<Market> markets = Stream.of(
                Market.builder()
                        .homeTeam(faker.random().nextDouble())
                        .draw(faker.random().nextDouble())
                        .awayTeam(faker.random().nextDouble())
                        .createdAt(OffsetDateTime.now().minusDays(1))
                        .build(),
                Market.builder()
                        .homeTeam(faker.random().nextDouble())
                        .draw(faker.random().nextDouble())
                        .awayTeam(faker.random().nextDouble())
                        .createdAt(OffsetDateTime.now())
                        .build()
        ).collect(Collectors.toSet());

        /* Act */
        Set<MarketDto> result = mapper.mapMarketsToMarketDtos(markets);

        /* Assert */
        assertThat(result).isNotNull().hasSize(markets.size());
        assertThat(result.stream()
                .map(MarketDto::getCreatedAt)
                .sorted(Comparator.naturalOrder())
                .toList())
                .isEqualTo(result.stream()
                        .map(MarketDto::getCreatedAt)
                        .sorted(Comparator.naturalOrder())
                        .toList());
    }

    @Test
    void should_map_event_set_to_get_bulletin_response_model() {
        // Given
        Event event = Event.builder()
                .homeTeam(faker.esports().team())
                .awayTeam(faker.esports().team())
                .startTime(OffsetDateTime.now())
                .markets(Stream.of(
                        Market.builder()
                                .homeTeam(faker.random().nextDouble())
                                .draw(faker.random().nextDouble())
                                .awayTeam(faker.random().nextDouble())
                                .createdAt(OffsetDateTime.now().minusDays(1))
                                .build(),
                        Market.builder()
                                .homeTeam(faker.random().nextDouble())
                                .draw(faker.random().nextDouble())
                                .awayTeam(faker.random().nextDouble())
                                .createdAt(OffsetDateTime.now())
                                .build()
                ).collect(Collectors.toSet()))
                .build();
        Set<Event> events = Set.of(event);

        // When
        GetBulletinResponseModel result = mapper.mapToGetBulletinResponseModel(events);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getBulletin()).hasSize(events.size());
        EventMarketDto eventMarketDto = result.getBulletin().iterator().next();
        assertThat(eventMarketDto.getEventName()).isEqualTo(event.getHomeTeam() + " - " + event.getAwayTeam());
        assertThat(eventMarketDto.getStartTime()).isEqualTo(event.getStartTime());
        assertThat(eventMarketDto.getMarkets()).hasSize(event.getMarkets().size());
        assertThat(eventMarketDto.getMarkets().stream()
                .map(MarketDto::getCreatedAt)
                .sorted(Comparator.naturalOrder())
                .toList())
                .isEqualTo(eventMarketDto.getMarkets().stream()
                        .map(MarketDto::getCreatedAt)
                        .sorted(Comparator.naturalOrder())
                        .toList());
    }
}

