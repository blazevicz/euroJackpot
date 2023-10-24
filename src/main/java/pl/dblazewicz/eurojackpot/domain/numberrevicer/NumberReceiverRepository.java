package pl.dblazewicz.eurojackpot.domain.numberrevicer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NumberReceiverRepository extends MongoRepository<Ticket, Long> {

    @NonNull
    Ticket save(Ticket ticket);

    List<Ticket> findAllByLocalDateTime(LocalDateTime localDateTime);
}
