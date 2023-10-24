package pl.dblazewicz.eurojackpot.domain.numbergenerator;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
class Draw {
    private Set<Integer> drawResultWhereMaxNumber50;
    private Set<Integer> drawResultWhereMaxNumber12;
    private LocalDateTime drawTime;
}
