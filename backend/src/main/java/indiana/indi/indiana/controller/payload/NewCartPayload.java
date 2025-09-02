package indiana.indi.indiana.controller.payload;

import indiana.indi.indiana.dto.CardItemDto;
import indiana.indi.indiana.entity.User;

import java.util.List;

public record NewCartPayload(User user, List<CardItemDto> games) {
}
