package indiana.indi.indiana.dto;

import java.util.List;

public record CategoryDto(Long id, String title, List<CardItemDto> games) {
}
