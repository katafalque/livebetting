package com.example.livebetting.utils;

import com.example.livebetting.data.model.dto.GeneratedOddsDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OddGenerator {
    private static final Random rng = new Random();

    private OddGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static GeneratedOddsDto generateOdds() {
        List<Double> randomOdds = new ArrayList<>();
        randomOdds.add(rng.nextDouble(1, 3));
        randomOdds.add(rng.nextDouble(1, 5));
        randomOdds.add(rng.nextDouble(1, 9));
        Collections.shuffle(randomOdds);
        return GeneratedOddsDto.builder()
                .homeTeamOdd(randomOdds.get(0))
                .drawOdd(randomOdds.get(1))
                .awayTeamOdd(randomOdds.get(2))
                .build();
    }
}
