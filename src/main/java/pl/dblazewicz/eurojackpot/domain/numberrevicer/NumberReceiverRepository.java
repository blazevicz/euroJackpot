package pl.dblazewicz.eurojackpot.domain.numberrevicer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class NumberReceiverRepository implements NumberReceiverDAO {
    private final TicketMapper ticketMapper;
    private final TicketMongoRepository ticketMongoRepository;

    @Override
    public TicketDTO save(Ticket ticket) {
        return ticketMapper.mapToDTO(ticket);
    }

    @Override
    public List<TicketDTO> findAllByLocalDateTime(LocalDateTime localDateTime) {
        List<Ticket> allByLocalDateTime = ticketMongoRepository.findAllByLocalDateTime(localDateTime);
        return allByLocalDateTime.stream().map(ticketMapper::mapToDTO).toList();
    }
}