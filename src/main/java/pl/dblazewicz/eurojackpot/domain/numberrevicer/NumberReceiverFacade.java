package pl.dblazewicz.eurojackpot.domain.numberrevicer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.dblazewicz.eurojackpot.domain.numbergenerator.DrawDTO;
import pl.dblazewicz.eurojackpot.domain.numbergenerator.NumberGeneratorFacade;

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
    private final NumberGeneratorFacade numberGeneratorFacade;

    public DrawDTO makeDraw() {
        return numberGeneratorFacade.draw();
    }

    public TicketDTO receiveNumbersAndCreateTicket(
            Set<Integer> mainNumbers,
            Set<Integer> bonusNumbers) throws NumbersOutOfRangeException {
        numberValidator.numbersAreCorrectly(mainNumbers, bonusNumbers);
        LocalDateTime currentTime = LocalDateTime.now(clock);

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