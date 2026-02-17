package indiana.indi.indiana.dto.categories;

import indiana.indi.indiana.dto.games.CardItemDto;

import java.util.List;

public record CategoryPageDto(
        List<CardItemDto> cardItems,
        Integer page,
        Integer totalPages,
        Long totalElements
) {
}
