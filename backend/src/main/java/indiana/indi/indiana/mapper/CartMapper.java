package indiana.indi.indiana.mapper;

import indiana.indi.indiana.dto.CartDto;
import indiana.indi.indiana.dto.CartItemDto;
import indiana.indi.indiana.entity.Cart;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartDto toDto(Cart cart) {
        BigDecimal totalPrice = cart.getItems().stream()
                .map(item -> item.getGame().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartDto(
                cart.getId(),
                cart.getUser().getId(),
                cart.getItems().stream()
                        .map(item -> new CartItemDto(
                                item.getId(),
                                item.getGame().getId(),
                                item.getGame().getTitle(),
                                item.getGame().getPrice()))
                        .collect(Collectors.toList()),
                cart.getItems().size(),
                totalPrice
        );
    }
}
