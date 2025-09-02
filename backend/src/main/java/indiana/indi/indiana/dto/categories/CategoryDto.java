package indiana.indi.indiana.dto.categories;

import indiana.indi.indiana.dto.games.CardItemDto;

import java.util.List;

public record CategoryDto(Long id, String title, List<CardItemDto> games) {
}
