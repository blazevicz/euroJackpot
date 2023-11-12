package pl.dblazewicz.eurojackpot.domain.numberrevicer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketMongoRepository extends MongoRepository<Ticket, Integer> {

    List<Ticket> findAllByLocalDateTime(LocalDateTime localDateTime);
}
