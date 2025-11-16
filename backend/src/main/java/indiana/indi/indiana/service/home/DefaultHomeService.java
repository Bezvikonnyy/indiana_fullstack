package indiana.indi.indiana.service.home;

import indiana.indi.indiana.dto.games.CardItemDto;
import indiana.indi.indiana.dto.news.NewsDto;
import indiana.indi.indiana.dtoInterface.games.CardItemDtoInter;
import indiana.indi.indiana.dtoInterface.news.NewsDtoInter;
import indiana.indi.indiana.mapperInterface.games.CardItemMapper;
import indiana.indi.indiana.mapperInterface.news.NewsMapper;
import indiana.indi.indiana.repository.games.GameRepository;
import indiana.indi.indiana.repository.news.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultHomeService implements HomeService {

    private final NewsRepository newsRepository;
    private final GameRepository gameRepository;
    private final NewsMapper newsMapper;
    private final CardItemMapper itemMapper;

    @Override
    public Set<CardItemDto> gamesLatestArrivals(Long userId) {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
        Set<CardItemDtoInter> gameInter = gameRepository.getGameByHome(userId,pageable).toSet();
        return gameInter.stream().map(itemMapper::toDto).collect(Collectors.toSet());
    }

    @Override
    public Set<NewsDto> newsLatestArrivals() {
        Pageable pageable = PageRequest.of(0, 3, Sort.by("createdAt").descending());
        Set<NewsDtoInter> newsInter = newsRepository.getNewsByHome(pageable).toSet();
        return newsInter.stream().map(newsMapper::toDto).collect(Collectors.toSet());
    }

    @Override
    public Set<CardItemDto> gamesDiscounts(Long userId) {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("price").descending());
        Set<CardItemDtoInter> gameInter = gameRepository.getGameByHome(userId,pageable).toSet();
        return gameInter.stream().map(itemMapper::toDto).collect(Collectors.toSet());
    }
}
