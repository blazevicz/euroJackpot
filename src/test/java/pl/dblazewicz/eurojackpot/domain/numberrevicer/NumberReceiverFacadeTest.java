package pl.dblazewicz.eurojackpot.domain.numberrevicer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.dblazewicz.eurojackpot.domain.numbergenerator.NumberGeneratorFacade;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NumberReceiverFacadeTest {
    @InjectMocks
    private NumberReceiverFacade numberReceiverFacade;
    @Mock
    private NumberValidator numberValidator;
    @Mock
    private NumberReceiverRepository numberReceiverRepository;
    @Mock
    private TicketMapper ticketMapper;
    @Mock
    private Clock clock;
    @Mock
    private NumberGeneratorFacade numberGeneratorFacade;

    @BeforeEach
    void setUp() {
        var fixedClock = Clock.fixed(
                LocalDateTime.of(2023, 1, 1, 12, 0, 0, 0)
                        .toInstant(ZoneOffset.UTC),
                ZoneId.systemDefault());

        numberReceiverFacade = new NumberReceiverFacade(
                numberValidator,
                numberReceiverRepository,
                ticketMapper,
                fixedClock,
                numberGeneratorFacade
        );
    }

    @Test
    void shouldSaveWithCorrectDateTime() throws NumbersOutOfRangeException {
        Set<Integer> mainNumbers = Set.of(1, 2, 3, 4, 5);
        Set<Integer> bonusNumbers = Set.of(1, 2);
        TicketDTO ticket = TicketDTO.builder()
                .localDateTime(LocalDateTime.of(2023, 1, 1, 12, 0, 0))
                .bonusNumbers(Set.of())
                .mainNumbers(Set.of())
                .ticketId(UUID.randomUUID())
                .build();
        when(numberReceiverRepository.save(any(Ticket.class))).thenReturn(ticket);
        TicketDTO ticketDTO = numberReceiverFacade.receiveNumbersAndCreateTicket(mainNumbers, bonusNumbers);
        Assertions.assertEquals(ticketDTO.localDateTime(), ticket.localDateTime());
    }

    @Test
    void shouldReturnSavedObject() {
        Ticket ticket = Ticket.builder()
                .ticketId(UUID.fromString("550e8400-e29b-41d4-a716-77777"))
                .build();

        TicketDTO ticketDTO = TicketDTO.builder()
                .ticketId(UUID.fromString("550e8400-e29b-41d4-a716-77777"))
                .build();
        LocalDateTime localDateTime = LocalDateTime.now();
        when(numberReceiverRepository.findAllByLocalDateTime(any())).thenReturn(List.of(ticketDTO));
        var tickets = numberReceiverFacade.usersTickets(localDateTime);
        Assertions.assertTrue(tickets.contains(ticketDTO));
    }

    @Test
    void shouldFindObjectInDatabase() {
        LocalDateTime localDateTime = LocalDateTime.now();
        when(numberReceiverRepository.findAllByLocalDateTime(any())).thenReturn(List.of(TicketDTO.builder().build()));
        var tickets = numberReceiverFacade.usersTickets(localDateTime);
        Assertions.assertEquals(1, tickets.size());
    }

    @Test
    void shouldFindMultipleObjectsInDatabase() {
        LocalDateTime localDateTime = LocalDateTime.now();
        when(numberReceiverRepository.findAllByLocalDateTime(any())).thenReturn(List.of(TicketDTO.builder().build(),
                TicketDTO.builder().build()));
        var tickets = numberReceiverFacade.usersTickets(localDateTime);
        Assertions.assertEquals(2, tickets.size());
    }

    @Test
    void shouldCreateTicketWithMainNumbersBonusNumbersAndTime() throws NumbersOutOfRangeException {
        Set<Integer> mainNumbers = Set.of(1, 2, 3, 4, 5);
        Set<Integer> bonusNumbers = Set.of(1, 2);
        TicketDTO ticketDTO = TicketDTO.builder()
                .mainNumbers(mainNumbers)
                .ticketId(UUID.randomUUID())
                .bonusNumbers(bonusNumbers)
                .localDateTime(LocalDateTime.now())
                .build();
        when(numberReceiverRepository.save(any())).thenReturn(ticketDTO);
        TicketDTO ticket = numberReceiverFacade.receiveNumbersAndCreateTicket(mainNumbers, bonusNumbers);
        Assertions.assertFalse(ticket.mainNumbers().isEmpty());
        Assertions.assertFalse(ticket.bonusNumbers().isEmpty());
        Assertions.assertNotNull(ticket.localDateTime());
    }

    @Test
    void shouldCreateTicketWithFiveMainNumbers() throws NumbersOutOfRangeException {
        Set<Integer> mainNumbers = Set.of(1, 2, 3, 4, 5);
        Set<Integer> bonusNumbers = Set.of(1, 2);
        TicketDTO ticketDTO = TicketDTO.builder()
                .mainNumbers(mainNumbers)
                .ticketId(UUID.randomUUID())
                .bonusNumbers(bonusNumbers)
                .localDateTime(LocalDateTime.now())
                .build();
        when(numberReceiverRepository.save(any())).thenReturn(ticketDTO);

        TicketDTO ticket = numberReceiverFacade.receiveNumbersAndCreateTicket(mainNumbers, bonusNumbers);
        Assertions.assertEquals(5, ticket.mainNumbers().size());
    }

    @Test
    void shouldSuccessWhenUserGaveTwoBonusNumbers() throws NumbersOutOfRangeException {
        Set<Integer> mainNumbers = Set.of(1, 2, 3, 4, 5);
        Set<Integer> bonusNumbers = Set.of(1, 2);
        TicketDTO ticketDTO = TicketDTO.builder()
                .mainNumbers(mainNumbers)
                .ticketId(UUID.randomUUID())
                .bonusNumbers(bonusNumbers)
                .localDateTime(LocalDateTime.now())
                .build();
        when(numberReceiverRepository.save(any())).thenReturn(ticketDTO);
        TicketDTO ticket = numberReceiverFacade.receiveNumbersAndCreateTicket(mainNumbers, bonusNumbers);
        Assertions.assertEquals(2, ticket.bonusNumbers().size());
    }
}