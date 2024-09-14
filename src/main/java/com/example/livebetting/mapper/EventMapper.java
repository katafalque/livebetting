package com.example.livebetting.mapper;

import com.example.livebetting.data.entity.Event;
import com.example.livebetting.data.entity.Market;
import com.example.livebetting.data.model.request.AddEventRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(source = "homeTeamOdd", target = "homeTeam")
    @Mapping(source = "awayTeamOdd", target = "awayTeam")
    @Mapping(source = "drawOdd", target = "draw")
    @Mapping(target = "createdAt", expression = "java(java.time.OffsetDateTime.now())")
    Market toMarket(AddEventRequestModel requestModel);

    @Mapping(source = "league", target = "league")
    @Mapping(source = "homeTeam", target = "homeTeam")
    @Mapping(source = "awayTeam", target = "awayTeam")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(target = "market", expression = "java(createMarketWithBidirectionalRelationship(requestModel))")
    Event toEvent(AddEventRequestModel requestModel);

    default Market createMarketWithBidirectionalRelationship(AddEventRequestModel requestModel) {
        Market market = toMarket(requestModel);
        Event event = Event.builder().build();
        market.setEvent(event);
        return market;
    }

    default Event createEventWithBidirectionalRelationship(AddEventRequestModel requestModel) {
        Event event = toEvent(requestModel);
        Market market = event.getMarket();
        market.setEvent(event);
        return event;
    }
}


