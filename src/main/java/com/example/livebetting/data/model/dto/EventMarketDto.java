package com.example.livebetting.data.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
public class EventMarketDto {
    private String league;
    private String eventName;
    private OffsetDateTime startTime;
    private Set<MarketDto> markets;
}
