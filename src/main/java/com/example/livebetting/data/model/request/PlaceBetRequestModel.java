package com.example.livebetting.data.model.request;

import com.example.livebetting.data.model.dto.BetModelDto;
import com.example.livebetting.utils.validators.ValidatePlaceBetRequestModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ValidatePlaceBetRequestModel
public class PlaceBetRequestModel {
    @JsonProperty("events")
    private List<BetModelDto> events;
    @JsonProperty("bet_amount")
    private int betAmount;
    @JsonProperty("multi_coupon_count")
    private int multiCouponCount;
}
