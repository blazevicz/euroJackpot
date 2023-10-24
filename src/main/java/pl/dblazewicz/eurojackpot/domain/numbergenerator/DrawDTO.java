package pl.dblazewicz.eurojackpot.domain.numbergenerator;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record DrawDTO(Set<Integer> drawResultWhereMaxNumber50,
                      Set<Integer> drawResultWhereMaxNumber12,
                      LocalDateTime drawTime) {
}
