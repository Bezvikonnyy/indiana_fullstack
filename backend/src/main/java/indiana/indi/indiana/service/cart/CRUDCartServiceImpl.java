package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.dto.cartAndPay.CartDto;
import indiana.indi.indiana.dtoInterface.cartAndPay.CartDtoInter;
import indiana.indi.indiana.dtoInterface.cartAndPay.CartItemDtoInter;
import indiana.indi.indiana.mapperInterface.cartAndPay.CartMapper;
import indiana.indi.indiana.repository.cartAndPay.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CRUDCartServiceImpl implements CRUDCartService{

    private final CartRepository cartRepository;
    private final CartMapper mapper;

    @Override
    @Transactional
    public CartDto getCart(Long userId) {
        CartDtoInter cartDtoInter= cartRepository.getCartByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found."));
        Set<CartItemDtoInter> cartItemsDtoInter = cartRepository.getCartItemsByUserId(userId);
        return mapper.toDto(cartDtoInter, cartItemsDtoInter);
    }

    @Override
    public void clearCart(Long userId) {
        cartRepository.clearCartByUserId(userId);
    }
}
