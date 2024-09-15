package com.example.livebetting.data.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
public class MarketDto {
    private double homeTeamOdd;
    private double drawOdd;
    private double awayTeamOdd;
    private OffsetDateTime createdAt;
}
