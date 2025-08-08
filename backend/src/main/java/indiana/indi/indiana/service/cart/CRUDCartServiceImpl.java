package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.entity.Cart;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.repository.CartRepository;
import indiana.indi.indiana.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CRUDCartServiceImpl implements CRUDCartService{

    private final CartRepository cartRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public Cart getCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found."));
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
    }

    @Override
    public void clearCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found."));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new EntityNotFoundException("Cart not found."));
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
