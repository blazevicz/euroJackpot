package pl.dblazewicz.eurojackpot.domain.numberrevicer;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
public record TicketDTO(
        UUID ticketId,
        Set<Integer> mainNumbers,
        Set<Integer> bonusNumbers,
        LocalDateTime localDateTime) {
}
