package indiana.indi.indiana.mapperInterface.cartAndPay;

import indiana.indi.indiana.dto.cartAndPay.CartItemDto;
import indiana.indi.indiana.dtoInterface.cartAndPay.CartItemDtoInter;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public CartItemDto toDto(CartItemDtoInter dtoInter){
        return new CartItemDto(
                dtoInter.getId(),
                dtoInter.getGameId(),
                dtoInter.getGameTitle(),
                dtoInter.getPrice(),
                dtoInter.getImageUrl());
    }
}
