package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.entity.Cart;
import indiana.indi.indiana.repository.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CRUDCartServiceImpl implements CRUDCartService{

    private final CartRepository cartRepository;

    @Override
    public Cart getCart(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart not found."));
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart not found."));
        cart.getCarts().clear();
        cartRepository.save(cart);
    }
}
