package indiana.indi.indiana.dto.games;

import java.util.List;

public record GamesPageDto(
        List<CardItemDto> content,
        int page,
        int totalPages,
        long totalElements
) {}
