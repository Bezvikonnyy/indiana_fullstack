package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.controller.payload.CartItemPayload;
import indiana.indi.indiana.entity.Cart;
import indiana.indi.indiana.entity.CartItem;
import indiana.indi.indiana.entity.Game;
import indiana.indi.indiana.repository.CartItemRepository;
import indiana.indi.indiana.repository.CartRepository;
import indiana.indi.indiana.service.game.GameService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CRUDCartItemServiceImpl implements CRUDCartItemService{

    private final CartItemRepository cartItemRepository;

    private final GameService gameService;

    private final CartRepository cartRepository;

    @Override
    public CartItem addCartItem(CartItemPayload payload, Long cartId) {
        Game game = gameService.getGame(payload.id());
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found."));
        boolean isCartItem = cart.getCarts().stream().anyMatch(c -> c.getGame().getId().equals(game.getId()));
        if(isCartItem) {
            throw new IllegalStateException("Game already in cart.");
        }
        CartItem cartItem = CartItem.builder().game(game).cart(cart).build();
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void removeCartItem(CartItemPayload payload, Long cartId) {
        Game game = gameService.getGame(payload.id());
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found."));
        CartItem cartItem = cart.getCarts().stream().filter(item -> item.getGame().getId().equals(game.getId()))
                .findFirst().orElseThrow(() -> new EntityNotFoundException("CartItem not found."));
        cart.getCarts().remove(cartItem);
        cartItemRepository.delete(cartItem);
    }

    @Override
    public CartItem getCartItem(Long id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("CartItem not found."));
    }
}
