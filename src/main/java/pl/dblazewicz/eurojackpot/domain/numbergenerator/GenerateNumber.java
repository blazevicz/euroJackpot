package pl.dblazewicz.eurojackpot.domain.numbergenerator;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
class GenerateNumber {
    private static final Random RANDOM = new Random();

    public Set<Integer> winningNumbers(Integer bound, Integer limit) {
        return Stream.generate(() -> generateRandomNumber(bound))
                .distinct()
                .limit(limit)
                .collect(Collectors.toSet());
    }
    private Integer generateRandomNumber(Integer bound) {
        return RANDOM.nextInt(bound);
    }
}
