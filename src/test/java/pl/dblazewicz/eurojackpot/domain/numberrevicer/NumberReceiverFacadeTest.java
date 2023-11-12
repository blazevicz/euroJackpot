package pl.dblazewicz.eurojackpot.domain.numberrevicer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
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

    @BeforeEach
    void setUp() {
        Clock fixedClock = Clock.fixed(
                Instant.parse("2023-11-05T18:22:21.131225Z"),
                ZoneId.of("UTC")
        );

        numberReceiverFacade = new NumberReceiverFacade(
                numberValidator,
                numberReceiverRepository,
                ticketMapper,
                fixedClock
        );
    }

    @Test
    void shouldSaveWithCorrectDateTime() throws NumbersOutOfRangeException {
        TicketDTO ticket = TicketDTO.builder()
                .localDateTime(LocalDateTime.of(2023, 1, 1, 12, 0, 0))
                .bonusNumbers(Set.of())
                .mainNumbers(Set.of())
                .ticketId(UUID.randomUUID())
                .build();

        when(numberReceiverRepository.save(any(Ticket.class))).thenReturn(ticket);

        TicketDTO ticketDTO = numberReceiverFacade.receiveNumbersAndCreateTicket(Set.of(), Set.of());

        assertEquals(ticketDTO.localDateTime(), ticket.localDateTime());
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
        when(ticketMapper.mapToDTO(any())).thenReturn(ticketDTO);
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
        when(numberReceiverRepository.findAllByLocalDateTime(any())).thenReturn(List.of(TicketDTO.builder().build(), TicketDTO.builder().build()));
        var tickets = numberReceiverFacade.usersTickets(localDateTime);
        Assertions.assertEquals(2, tickets.size());
    }

    @Test
    void shouldCreateTicketWithMainNumbersBonusNumbersAndTime() throws NumbersOutOfRangeException {
        Set<Integer> mainNumbers = Set.of(1, 2, 3, 4, 5);
        Set<Integer> bonusNumbers = Set.of(1, 2);
        TicketDTO ticket = numberReceiverFacade.receiveNumbersAndCreateTicket(mainNumbers, bonusNumbers);
        Assertions.assertFalse(ticket.mainNumbers().isEmpty());
        Assertions.assertFalse(ticket.bonusNumbers().isEmpty());
        Assertions.assertNotNull(ticket.localDateTime());
    }

    @Test
    void shouldCreateTicketWithFiveMainNumbers() throws NumbersOutOfRangeException {
        Set<Integer> mainNumbers = Set.of(1, 2, 3, 4, 5);
        Set<Integer> bonusNumbers = Set.of(1, 2);
        TicketDTO ticket = numberReceiverFacade.receiveNumbersAndCreateTicket(mainNumbers, bonusNumbers);
        Assertions.assertEquals(5, ticket.mainNumbers().size());
    }

    @Test
    void shouldSuccessWhenUserGaveTwoBonusNumbers() throws NumbersOutOfRangeException {
        Set<Integer> mainNumbers = Set.of(1, 2, 3, 4, 5);
        Set<Integer> bonusNumbers = Set.of(1, 2);
        TicketDTO ticket = numberReceiverFacade.receiveNumbersAndCreateTicket(mainNumbers, bonusNumbers);
        Assertions.assertEquals(2, ticket.bonusNumbers().size());
    }
}