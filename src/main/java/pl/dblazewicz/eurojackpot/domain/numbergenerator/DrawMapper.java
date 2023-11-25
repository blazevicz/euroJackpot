package pl.dblazewicz.eurojackpot.domain.numbergenerator;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface DrawMapper {
    DrawDTO mapToDTO(Draw ticket);

    Draw mapFromDTO(DrawDTO ticketDTO);
}

