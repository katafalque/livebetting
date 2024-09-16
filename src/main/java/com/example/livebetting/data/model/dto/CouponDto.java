package com.example.livebetting.data.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponDto {
    @JsonProperty("total_odd")
    private double odd;
    @JsonProperty("potential_winnings")
    private double potentialWinnings;
    @JsonProperty("tickets")
    private List<TicketDto> tickets;
}
