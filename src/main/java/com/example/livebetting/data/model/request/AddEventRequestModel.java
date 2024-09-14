package com.example.livebetting.data.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
public class AddEventRequestModel {
    private String league;
    @JsonProperty("home_team")
    private String homeTeam;
    @JsonProperty("away_team")
    private String awayTeam;
    @JsonProperty("start_time")
    private OffsetDateTime startTime;
}
