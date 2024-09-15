package com.example.livebetting.mapper;

import com.example.livebetting.data.entity.Event;
import com.example.livebetting.data.entity.Market;
import com.example.livebetting.data.model.dto.EventMarketDto;
import com.example.livebetting.data.model.dto.MarketDto;
import com.example.livebetting.data.model.response.GetBulletinResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BulletinMapper {

    @Mapping(source = "startTime", target = "startTime")
    @Mapping(target = "eventName", expression = "java(event.getHomeTeam() + \" - \" + event.getAwayTeam())")
    @Mapping(source = "markets", target = "markets")
    EventMarketDto eventToEventMarketDto(Event event);

    default GetBulletinResponseModel mapToGetBulletinResponseModel(Set<Event> events) {
        Set<EventMarketDto> eventMarketDtos = events.stream()
                .map(this::eventToEventMarketDto)
                .collect(Collectors.toSet());
        return GetBulletinResponseModel.builder()
                .bulletin(eventMarketDtos)
                .build();
    }

    @Mapping(source = "homeTeam", target = "homeTeamOdd")
    @Mapping(source = "draw", target = "drawOdd")
    @Mapping(source = "awayTeam", target = "awayTeamOdd")
    @Mapping(source = "createdAt", target = "createdAt")
    MarketDto marketToMarketDto(Market market);

    default Set<MarketDto> mapMarketsToMarketDtos(Set<Market> markets) {
        return markets.stream()
                .map(this::marketToMarketDto)
                .sorted(Comparator.comparing(MarketDto::getCreatedAt))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}

