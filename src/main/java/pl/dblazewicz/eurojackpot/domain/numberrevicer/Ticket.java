package pl.dblazewicz.eurojackpot.domain.numberrevicer;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
record Ticket(
        UUID ticketId,
        Set<Integer> mainNumbers,
        Set<Integer> bonusNumbers,
        LocalDateTime localDateTime) {
}
