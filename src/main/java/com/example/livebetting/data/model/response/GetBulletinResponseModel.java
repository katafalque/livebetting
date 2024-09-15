package com.example.livebetting.data.model.response;

import com.example.livebetting.data.model.dto.EventMarketDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Getter
@Setter
@Builder
@Jacksonized
public class GetBulletinResponseModel {
    private Set<EventMarketDto> bulletin;
}
