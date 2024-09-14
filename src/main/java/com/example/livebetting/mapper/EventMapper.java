package com.example.livebetting.mapper;

import com.example.livebetting.data.entity.Event;
import com.example.livebetting.data.model.request.AddEventRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(source = "league", target = "league")
    @Mapping(source = "homeTeam", target = "homeTeam")
    @Mapping(source = "awayTeam", target = "awayTeam")
    @Mapping(source = "startTime", target = "startTime")
    Event toEvent(AddEventRequestModel requestModel);
}


