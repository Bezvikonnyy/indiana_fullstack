package indiana.indi.indiana.controller.payload;

import indiana.indi.indiana.dto.GameDto;
import indiana.indi.indiana.entity.User;

import java.util.List;

public record NewCartPayload(User user, List<GameDto> games) {
}
