package indiana.indi.indiana.mapper.cartAndPay;

import indiana.indi.indiana.dto.cartAndPay.CartDto;
import indiana.indi.indiana.dto.cartAndPay.CartItemDto;
import indiana.indi.indiana.entity.cartAndPay.Cart;
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
