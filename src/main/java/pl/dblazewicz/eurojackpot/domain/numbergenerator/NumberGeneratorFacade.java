package pl.dblazewicz.eurojackpot.domain.numbergenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NumberGeneratorFacade {

    private final GenerateNumber generateNumber;
    private final GeneratedNumberRepository generatedNumberRepository;
    public DrawDTO draw() {
        Set<Integer> resultMax50 = generateNumber.winningNumbers(50, 5);
        Set<Integer> resultMax12 = generateNumber.winningNumbers(12, 2);
        Draw draw = Draw.builder()
                .drawResultWhereMaxNumber12(resultMax12)
                .drawResultWhereMaxNumber50(resultMax50)
                .drawTime(LocalDateTime.now())
                .build();
        return generatedNumberRepository.save(draw);
    }
}
