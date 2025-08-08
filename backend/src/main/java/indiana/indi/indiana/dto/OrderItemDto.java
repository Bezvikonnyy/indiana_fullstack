package indiana.indi.indiana.dto;

import java.math.BigDecimal;

public record OrderItemDto(Long id, Long gameId, String gameTitle, BigDecimal price, int quantity){}
