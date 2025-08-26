package indiana.indi.indiana.service.game;

import indiana.indi.indiana.controller.payload.EditGamePayload;
import indiana.indi.indiana.controller.payload.NewGamePayload;
import indiana.indi.indiana.dto.GameFullDto;
import indiana.indi.indiana.entity.Game;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.mapper.GameFullMapper;
import indiana.indi.indiana.service.user.CustomUserDetails;
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

    private final GameFullMapper mapper;

    public GameFullDto createGame(NewGamePayload payload,
                                  MultipartFile imageFile,
                                  MultipartFile gameFile,
                                  User author){
        Game game = crudService.createGame(payload, imageFile, gameFile, author);
        return mapper.toDto(game);
    }

    public GameFullDto editGame(Long id,
                                EditGamePayload payload,
                                MultipartFile imageFile,
                                MultipartFile gameFile,
                                CustomUserDetails userDetails){
        Game game = crudService.editGame(id, payload, imageFile, gameFile, userDetails);
        return mapper.toDto(game);
    }

    public GameFullDto getGame(Long id){ return mapper.toDto(crudService.getGameById(id));}

    public List<GameFullDto> getAllGames(String filter) {
        List<Game> games = gameService.findAllGames(filter);
        return games.stream().map(mapper::toDto).collect(Collectors.toList());
    }
    public void deleteGame(Long id, CustomUserDetails user){ crudService.deleteGame(id,user);}
}
