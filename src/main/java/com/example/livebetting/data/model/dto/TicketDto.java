package com.example.livebetting.data.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    @JsonProperty("event_name")
    private String eventName;
    @JsonProperty("selection")
    private String selection;
    @JsonProperty("odd")
    private double odd;
}
