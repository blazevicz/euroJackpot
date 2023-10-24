package pl.dblazewicz.eurojackpot.domain.numberrevicer;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumberValidatorTest {
    private final NumberValidator numberValidator = new NumberValidator();

    @Test
    void shouldThrowExceptionForInvalidMainNumbers() {
        Set<Integer> mainNumbers = Set.of(1, 2, 3, 4, 51);
        Set<Integer> bonusNumbers = Set.of(1, 2);

        assertThrows(NumbersOutOfRangeException.class, () -> {
            numberValidator.numbersAreCorrectly(mainNumbers, bonusNumbers);
        });
    }

    @Test
    void shouldThrowExceptionForInvalidBonusNumbers() {
        Set<Integer> mainNumbers = Set.of(1, 2, 3, 4, 5);
        Set<Integer> bonusNumbers = Set.of(1, 12);

        assertThrows(NumbersOutOfRangeException.class, () -> {
            numberValidator.numbersAreCorrectly(mainNumbers, bonusNumbers);
        });
    }

    @Test
    void shouldNotThrowExceptionForValidNumbers() {
        Set<Integer> mainNumbers = Set.of(1, 2, 3, 4, 5);
        Set<Integer> bonusNumbers = Set.of(1, 2);

        assertDoesNotThrow(() -> {
            numberValidator.numbersAreCorrectly(mainNumbers, bonusNumbers);
        });
    }
}