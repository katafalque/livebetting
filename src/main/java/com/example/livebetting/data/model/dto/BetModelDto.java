package com.example.livebetting.data.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BetModelDto {
    @JsonProperty("event_id")
    private UUID eventId;
    @JsonProperty("selection")
    private String selection;
}
