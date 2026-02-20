package indiana.indi.indiana.service.game;

import indiana.indi.indiana.controller.payload.EditGamePayload;
import indiana.indi.indiana.controller.payload.NewGamePayload;

import indiana.indi.indiana.dto.games.GameDetailsDto;
import indiana.indi.indiana.entity.games.Game;
import org.springframework.web.multipart.MultipartFile;

public interface CRUDGameService {

    GameDetailsDto createGame(
            NewGamePayload payload,
            MultipartFile imageFile,
            MultipartFile gaveFile,
            Long userId);

    Game getGameById(Long gameId);

    GameDetailsDto editGame(
            Long id,
            EditGamePayload payload,
            MultipartFile imageFile,
            MultipartFile gameFile,
            Long userId
    );

    void deleteGame(Long id,Long userId);
}
