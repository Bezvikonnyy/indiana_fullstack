package indiana.indi.indiana.service.game;

import indiana.indi.indiana.controller.payload.EditGamePayload;
import indiana.indi.indiana.controller.payload.NewGamePayload;
import indiana.indi.indiana.dto.games.CardItemDto;
import indiana.indi.indiana.dto.games.GameDetailsDto;
import indiana.indi.indiana.dto.games.GamesPageDto;
import indiana.indi.indiana.dtoInterface.games.CardItemDtoInter;
import indiana.indi.indiana.dtoInterface.games.GameDetailsDtoInter;
import indiana.indi.indiana.mapper.games.CardItemMapper;
import indiana.indi.indiana.mapper.games.GameDetailsMapper;
import indiana.indi.indiana.repository.categories.CategoryRepository;
import indiana.indi.indiana.repository.games.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GameForControllerService {

    private final CRUDGameServiceImpl crudService;
    private final GameRatingService ratingService;
    private final CategoryRepository categoryRepository;
    private final GameRepository gameRepository;
    private final GameDetailsMapper mapper;
    private final CardItemMapper itemMapper;

    public GameDetailsDto createGame(NewGamePayload payload,
                                     MultipartFile imageFile,
                                     MultipartFile gameFile,
                                     Long userId) {
        return crudService.createGame(payload, imageFile, gameFile, userId);
    }

    public GameDetailsDto editGame(Long id,
                                   EditGamePayload payload,
                                   MultipartFile imageFile,
                                   MultipartFile gameFile,
                                   Long userId) {
        return crudService.editGame(id, payload, imageFile, gameFile, userId);
    }

    public GameDetailsDto getGame(Long userId, Long gameId) {
        return mapper.toDto(crudService.getGame(userId, gameId), categoryRepository.getCategoriesForGameDetails(gameId));
    }

    public void deleteGame(Long gameId, Long userId) {
        crudService.deleteGame(gameId, userId);
    }

    public GamesPageDto getLatestGames(int page, int size, Long userId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<CardItemDtoInter> gamesPage = gameRepository.findLatestGames(userId, LocalDateTime.now(), pageable);
        List<CardItemDto> games = gamesPage.stream().map(itemMapper::toDto).collect(Collectors.toList());
        return new GamesPageDto(
                games,
                gamesPage.getNumber(),
                gamesPage.getTotalPages(),
                gamesPage.getTotalElements()
        );
    }

    public GamesPageDto getDiscountGames(int page, int size, Long userId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("price").descending());
        Page<CardItemDtoInter> gameInter = gameRepository.findDiscountedGames(userId, LocalDateTime.now(), pageable);
        List<CardItemDto> games =  gameInter.stream().map(itemMapper::toDto).collect(Collectors.toList());
        return new GamesPageDto(
                games,
                gameInter.getNumber(),
                gameInter.getTotalPages(),
                gameInter.getTotalElements()
        );
    }

    public GamesPageDto getPopularsGames(int page, int size, Long userId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("averageRating").descending());
        Page<CardItemDtoInter> gameInter = gameRepository.findPopularGames(userId, LocalDateTime.now(), pageable);
        List<CardItemDto> games =  gameInter.stream().map(itemMapper::toDto).collect(Collectors.toList());
        return new GamesPageDto(
                games,
                gameInter.getNumber(),
                gameInter.getTotalPages(),
                gameInter.getTotalElements()
        );
    }

    public GameDetailsDto gameRating(Long userId, Long gameId, int ratingValue) {
        ratingService.rateGame(userId, gameId, ratingValue);
        GameDetailsDtoInter game = gameRepository.getGameDetailsById(userId, gameId, LocalDateTime.now())
                .orElseThrow(() -> new EntityNotFoundException("Game not found!"));
        return mapper.toDto(game, categoryRepository.getCategoriesForGameDetails(gameId));
    }
}
