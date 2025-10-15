package indiana.indi.indiana.service.game;

import indiana.indi.indiana.controller.payload.EditGamePayload;
import indiana.indi.indiana.controller.payload.NewGamePayload;

import indiana.indi.indiana.entity.games.Game;
import indiana.indi.indiana.entity.users.User;
import indiana.indi.indiana.service.user.customUser.CustomUserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface CRUDGameService {

    Game createGame(
            NewGamePayload payload,
            MultipartFile imageFile,
            MultipartFile gaveFile,
            User author);

    Game getGameById(Long gameId);

    Game editGame(
            Long id,
            EditGamePayload payload,
            MultipartFile imageFile,
            MultipartFile gameFile,
            CustomUserDetails userDetails
    );

    void deleteGame(Long id,CustomUserDetails userDetails);
}
