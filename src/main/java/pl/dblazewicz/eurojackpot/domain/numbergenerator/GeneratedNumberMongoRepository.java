package pl.dblazewicz.eurojackpot.domain.numbergenerator;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface GeneratedNumberMongoRepository extends MongoRepository<Draw, Long> {

    Optional<Draw> findByDrawTime(LocalDate localDate);

    Optional<Draw> findFirstByDrawTimeOrderByDrawTimeDesc();
}
