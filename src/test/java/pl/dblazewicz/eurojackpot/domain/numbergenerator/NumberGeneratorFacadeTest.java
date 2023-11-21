package pl.dblazewicz.eurojackpot.domain.numbergenerator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class NumberGeneratorFacadeTest {
    @Mock
    GenerateNumber generateNumberMock;
    @Mock
    GeneratedNumberRepository generatedNumberRepository;
    @InjectMocks
    NumberGeneratorFacade numberGeneratorFacade;

    @Test
    void shouldDrawCorrectly() {
        Mockito.when(generateNumberMock.winningNumbers(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Set.of());
        Mockito.when(generatedNumberRepository.save(any())).thenReturn(DrawDTO.builder().build());
        DrawDTO draw = numberGeneratorFacade.draw();
        Assertions.assertNotNull(draw);
    }

    @Test
    void drawShouldHaveDate() {
        Mockito.when(generateNumberMock.winningNumbers(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Set.of());
        Mockito.when(generatedNumberRepository.save(any())).thenReturn(DrawDTO.builder().drawTime(LocalDateTime.now()).build());
        DrawDTO draw = numberGeneratorFacade.draw();
        Assertions.assertNotNull(draw.drawTime());
    }

    @Test
    void drawShouldHaveResult() {
        Mockito.when(generateNumberMock.winningNumbers(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Set.of());
        Mockito.when(generatedNumberRepository.save(any())).thenReturn(
                DrawDTO.builder().
                        drawResultWhereMaxNumber12(Set.of())
                        .drawResultWhereMaxNumber50(Set.of())
                        .build());
        DrawDTO draw = numberGeneratorFacade.draw();
        Assertions.assertNotNull(draw.drawResultWhereMaxNumber12());
        Assertions.assertNotNull(draw.drawResultWhereMaxNumber50());
    }
}