package pl.dblazewicz.eurojackpot.domain.numberrevicer;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class NumberValidator {
    public void numbersAreCorrectly(Set<Integer> mainNumbers, Set<Integer> bonusNumbers) throws NumbersOutOfRangeException {
        var mainNumbersListWithCorrectNumbers = mainNumbers.stream()
                .filter(number -> number < 51)
                .filter(number -> number > 0)
                .toList()
                .size();
        var bonusNumbersListWithCorrectNumbers = bonusNumbers.stream()
                .filter(number -> number < 12)
                .filter(number -> number > 0)
                .toList()
                .size();
        if (mainNumbersListWithCorrectNumbers != 5 || bonusNumbersListWithCorrectNumbers != 2) {
            throw new NumbersOutOfRangeException("Received numbers are not correct ", mainNumbers, bonusNumbers);
        }
    }
}
