package com.example.livebetting.utils;

import com.example.livebetting.data.model.dto.GeneratedOddsDto;

import java.text.DecimalFormat;
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
                .homeTeamOdd(round(randomOdds.get(0)))
                .drawOdd(round(randomOdds.get(1)))
                .awayTeamOdd(round(randomOdds.get(2)))
                .build();
    }

    private static double round(double d) {
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        return Double.parseDouble(twoDecimals.format(d).replace(",", "."));
    }
}
