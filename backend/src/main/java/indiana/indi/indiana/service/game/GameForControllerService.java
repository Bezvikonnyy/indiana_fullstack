package indiana.indi.indiana.service.game;

import indiana.indi.indiana.controller.payload.EditGamePayload;
import indiana.indi.indiana.controller.payload.NewGamePayload;
import indiana.indi.indiana.dto.games.GameFullDto;
import indiana.indi.indiana.entity.games.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameForControllerService {

    private final CRUDGameServiceImpl crudService;
    private final GameService gameService;

    public GameFullDto createGame(NewGamePayload payload,
                                  MultipartFile imageFile,
                                  MultipartFile gameFile,
                                  Long userId){
        return crudService.createGame(payload, imageFile, gameFile, userId);
    }

    public GameFullDto editGame(Long id,
                                EditGamePayload payload,
                                MultipartFile imageFile,
                                MultipartFile gameFile,
                                Long userId){
        return crudService.editGame(id, payload, imageFile, gameFile, userId);
    }

    public GameFullDto getGame(Long gameId){ return crudService.getGame(gameId);}

    public List<GameFullDto> getAllGames(String filter) {
        List<Game> games = gameService.findAllGames(filter);
        return games.stream().map(game -> crudService.getGame(game.getId())).collect(Collectors.toList());
    }
    public void deleteGame(Long gameId, Long userId){ crudService.deleteGame(gameId, userId);}
}
