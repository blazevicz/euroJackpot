package pl.dblazewicz.eurojackpot.domain.numberrevicer;

import java.time.LocalDateTime;
import java.util.List;

interface NumberReceiverDAO {
    TicketDTO save(Ticket ticket);

    List<TicketDTO> findAllByLocalDateTime(LocalDateTime localDateTime);
}
