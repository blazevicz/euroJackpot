package pl.dblazewicz.eurojackpot.domain.numbergenerator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class GeneratedNumberRepository implements DrawDAO {
    private final DrawMapper drawMapper;
    private final GeneratedNumberMongoRepository generatedNumberMongoRepository;


    @Override
    public DrawDTO save(Draw draw) {
        Draw save = generatedNumberMongoRepository.save(draw);
        return drawMapper.mapToDTO(save);
    }

    @Override
    public Optional<DrawDTO> findWinningNumbersByDate(LocalDate date) {
        Optional<Draw> byDrawTime = generatedNumberMongoRepository.findByDrawTime(date);
        return byDrawTime.map(drawMapper::mapToDTO);
    }

    @Override
    public Optional<DrawDTO> findLastWinningNumbers() {
        Optional<Draw> firstByLocalDateFieldOrderByLocalDateFieldDesc =
                generatedNumberMongoRepository.findFirstByDrawTimeOrderByDrawTimeDesc();
        return firstByLocalDateFieldOrderByLocalDateFieldDesc.map(drawMapper::mapToDTO);
    }
}
