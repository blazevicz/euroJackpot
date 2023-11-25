package pl.dblazewicz.eurojackpot.domain.numbergenerator;

import java.time.LocalDate;
import java.util.Optional;

public interface DrawDAO {

    DrawDTO save(Draw draw);

    Optional<DrawDTO> findWinningNumbersByDate(LocalDate date);

    Optional<DrawDTO> findLastWinningNumbers();
}
