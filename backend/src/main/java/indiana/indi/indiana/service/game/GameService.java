package indiana.indi.indiana.service.game;

import indiana.indi.indiana.entity.games.Game;
import indiana.indi.indiana.repository.games.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public List<Game> findAllGames(String filter) {
        if(filter != null && !filter.isBlank()){
            return gameRepository.findAllByTitleLikeIgnoreCase("%" + filter + "%");
        } else {
            return gameRepository.findAll();
        }
    }

    public Game getGame(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with id " + id));
    }
}
