package pl.dblazewicz.eurojackpot.domain.numbergenerator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GenerateNumberTest {
    private final GenerateNumber generateNumber = new GenerateNumber();

    @Test
    void shouldGenerate() {
        int bound = 1;
        int limit = 1;
        Set<Integer> result = generateNumber.winningNumbers(bound, limit);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void shouldGenerate5Numbers() {
        int bound = 6;
        int limit = 5;
        Set<Integer> result = generateNumber.winningNumbers(bound, limit);
        Assertions.assertEquals(5, result.size());
    }

    @Test
    void shouldGenerate2Numbers() {
        int bound = 3;
        int limit = 2;
        Set<Integer> result = generateNumber.winningNumbers(bound, limit);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void maxNumberShouldBe50() {
        int bound = 51;
        int limit = 50;
        int maxValue = 50;
        Set<Integer> result = generateNumber.winningNumbers(bound, limit);
        assertTrue(result.stream().max(Comparator.naturalOrder()).orElseThrow() <= maxValue);
    }

    @Test
    void minNumberShouldBe1() {
        int bound = 101;
        int limit = 100;
        int minValue = 1;
        Set<Integer> result = generateNumber.winningNumbers(bound, limit);
        assertTrue(result.stream().max(Comparator.naturalOrder()).orElseThrow() >= minValue);
    }

}