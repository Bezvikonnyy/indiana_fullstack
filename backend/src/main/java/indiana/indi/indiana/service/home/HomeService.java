package indiana.indi.indiana.service.home;

import indiana.indi.indiana.dto.games.CardItemDto;
import indiana.indi.indiana.dto.news.NewsDto;

import java.util.Set;

public interface HomeService {
    Set<CardItemDto> gamesLatestArrivals(Long userId);

    Set<NewsDto> newsLatestArrivals();

    Set<CardItemDto> gamesDiscounts(Long userId);
}
