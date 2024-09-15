package com.example.livebetting.utils;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OddGeneratorTest {
    @Test
    void should_generate_random_odds() {
        /* Act */
        var result = OddGenerator.generateOdds();

        /* Assert */
        assertThat(result).isNotNull();
        // prevent sure bet
        assertTrue(result.getHomeTeamOdd() < 3 || result.getDrawOdd() < 3 || result.getAwayTeamOdd() < 3);
    }
}
