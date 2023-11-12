package pl.dblazewicz.eurojackpot.domain.numberrevicer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Clock;
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
    private final Clock clock;

    public TicketDTO receiveNumbersAndCreateTicket(
            Set<Integer> mainNumbers,
            Set<Integer> bonusNumbers) throws NumbersOutOfRangeException {

        numberValidator.numbersAreCorrectly(mainNumbers, bonusNumbers);
        LocalDateTime currentTime = LocalDateTime.now();

        Ticket ticket = Ticket.builder()
                .mainNumbers(mainNumbers)
                .bonusNumbers(bonusNumbers)
                .localDateTime(currentTime)
                .ticketId(UUID.randomUUID())
                .build();

        return numberReceiverRepository.save(ticket);
    }

    public List<TicketDTO> usersTickets(LocalDateTime localDateTime) {
        return numberReceiverRepository.findAllByLocalDateTime(localDateTime);
    }
}