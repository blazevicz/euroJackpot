package pl.dblazewicz.eurojackpot.domain.numberrevicer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @BeforeEach
    void setUp() {
        numberReceiverFacade = new NumberReceiverFacade(
                numberValidator,
                numberReceiverRepository,
                ticketMapper);
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
        when(numberReceiverRepository.findAllByLocalDateTime(any())).thenReturn(List.of(ticket));
        when(ticketMapper.mapToDTO(any())).thenReturn(ticketDTO);
        var tickets = numberReceiverFacade.usersTickets(localDateTime);
        Assertions.assertTrue(tickets.contains(ticketDTO));
    }

    @Test
    void shouldSaveObject() {
        LocalDateTime localDateTime = LocalDateTime.now();
        when(numberReceiverRepository.findAllByLocalDateTime(any())).thenReturn(List.of(Ticket.builder().build()));
        numberReceiverFacade.usersTickets(localDateTime);
        verify(numberReceiverRepository, times(1)).findAllByLocalDateTime(any());
    }

    @Test
    void shouldFindObjectInDatabase() {
        LocalDateTime localDateTime = LocalDateTime.now();
        when(numberReceiverRepository.findAllByLocalDateTime(any())).thenReturn(List.of(Ticket.builder().build()));
        var tickets = numberReceiverFacade.usersTickets(localDateTime);
        Assertions.assertEquals(1, tickets.size());
    }

    @Test
    void shouldFindMultipleObjectsInDatabase() {
        LocalDateTime localDateTime = LocalDateTime.now();
        when(numberReceiverRepository.findAllByLocalDateTime(any())).thenReturn(List.of(Ticket.builder().build(), Ticket.builder().build()));
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