package pl.dblazewicz.eurojackpot.domain.numberrevicer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NumberReceiverFacade {
    private final NumberValidator numberValidator;
    private final NumberReceiverRepository numberReceiverRepository;
    private final TicketMapper ticketMapper;

    public TicketDTO receiveNumbersAndCreateTicket(Set<Integer> mainNumbers, Set<Integer> bonusNumbers) throws NumbersOutOfRangeException {
        numberValidator.numbersAreCorrectly(mainNumbers, bonusNumbers);

        Ticket ticket = Ticket.builder()
                .mainNumbers(mainNumbers)
                .bonusNumbers(bonusNumbers)
                .localDateTime(LocalDateTime.now())
                .ticketId(UUID.randomUUID())
                .build();

        Ticket savedTicket = numberReceiverRepository.save(ticket);

        return TicketDTO.builder()
                .mainNumbers(mainNumbers)
                .bonusNumbers(bonusNumbers)
                .localDateTime(LocalDateTime.now())
                .ticketId(UUID.randomUUID())
                .build();
    }

    public List<TicketDTO> usersTickets(LocalDateTime localDateTime) {
        List<Ticket> allByLocalDateTime = numberReceiverRepository.findAllByLocalDateTime(localDateTime);
        return allByLocalDateTime.stream().map(ticketMapper::mapToDTO).toList();
    }
}
