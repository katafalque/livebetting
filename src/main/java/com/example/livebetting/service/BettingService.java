package com.example.livebetting.service;

import com.example.livebetting.data.model.request.PlaceBetRequestModel;

import java.time.OffsetDateTime;

public interface BettingService {
    void placeBet(PlaceBetRequestModel placeBetRequestModel, String username, OffsetDateTime betPlacingTime);
}
