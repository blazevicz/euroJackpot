package pl.dblazewicz.eurojackpot.domain.numberrevicer;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface TicketMapper {
    TicketDTO mapToDTO(Ticket ticket);

    Ticket mapFromDTO(TicketDTO ticketDTO);
}
