package indiana.indi.indiana.controller.payload;

import indiana.indi.indiana.dto.games.CardItemDto;
import indiana.indi.indiana.entity.users.User;

import java.util.List;

public record NewCartPayload(User user, List<CardItemDto> games) {
}
