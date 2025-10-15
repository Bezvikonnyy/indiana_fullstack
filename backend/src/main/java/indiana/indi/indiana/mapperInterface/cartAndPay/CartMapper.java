package indiana.indi.indiana.mapperInterface.cartAndPay;

import indiana.indi.indiana.dto.cartAndPay.CartDto;
import indiana.indi.indiana.dtoInterface.cartAndPay.CartDtoInter;
import indiana.indi.indiana.dtoInterface.cartAndPay.CartItemDtoInter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartMapper {

    private final CartItemMapper mapper;

    public CartDto toDto(CartDtoInter cartDtoInter, Set<CartItemDtoInter> cartItemsDtoInter){
        BigDecimal totalPrice = cartItemsDtoInter.stream()
                .map(CartItemDtoInter::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CartDto(
                cartDtoInter.getId(),
                cartDtoInter.getUserId(),
                cartItemsDtoInter.stream().map(mapper::toDto).collect(Collectors.toSet()),
                cartDtoInter.getTotalItems(),
                totalPrice
        );
    }
}
