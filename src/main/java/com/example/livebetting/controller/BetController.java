package com.example.livebetting.controller;

import com.example.livebetting.data.model.request.PlaceBetRequestModel;
import com.example.livebetting.data.model.response.GetUserCouponsResponseModel;
import com.example.livebetting.service.BettingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/bet")
public class BetController {
    private static final String USERNAME = "username";

    private final BettingService bettingService;

    @Autowired
    public BetController(BettingService bettingService) {
        this.bettingService = bettingService;
    }

    @PostMapping("/place")
    @Transactional(timeout = 2)
    public ResponseEntity<String> placeBet(@Valid @RequestBody PlaceBetRequestModel placeBetRequestModel,
                                           @RequestAttribute(USERNAME) String username) {
        bettingService.placeBet(placeBetRequestModel, username, OffsetDateTime.now().withNano(0));
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GetUserCouponsResponseModel> getBets(@RequestAttribute(USERNAME) String username) {
        GetUserCouponsResponseModel getUserCouponsResponseModel = bettingService.getUserCoupons(username);
        return new ResponseEntity<>(getUserCouponsResponseModel, HttpStatus.OK);
    }
}
