package com.example.livebetting.data.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GeneratedOddsDto {
    private double homeTeamOdd;
    private double drawOdd;
    private double awayTeamOdd;
}
