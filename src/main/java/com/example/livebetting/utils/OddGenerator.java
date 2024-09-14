package com.example.livebetting.utils;

import com.example.livebetting.data.model.dto.GeneratedOddsDto;

import java.util.Random;

public class OddGenerator {
    private static final Random rng = new Random();

    private OddGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static GeneratedOddsDto generateOdds() {
        // dummy odd generator to replace later
        GeneratedOddsDto odds = GeneratedOddsDto.builder().build();
        odds.setAwayTeamOdd(rng.nextDouble());
        odds.setHomeTeamOdd(rng.nextDouble());
        odds.setDrawOdd(rng.nextDouble());
        return odds;
    }
}
