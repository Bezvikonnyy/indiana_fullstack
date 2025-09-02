package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.controller.payload.CartItemPayload;
import indiana.indi.indiana.entity.cartAndPay.Cart;
import indiana.indi.indiana.entity.cartAndPay.CartItem;
import indiana.indi.indiana.entity.games.Game;
import indiana.indi.indiana.entity.users.User;
import indiana.indi.indiana.repository.cartAndPay.CartItemRepository;
import indiana.indi.indiana.repository.cartAndPay.CartRepository;
import indiana.indi.indiana.repository.users.UserRepository;
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

    private final UserRepository userRepository;

    @Override
    public CartItem addCartItem(CartItemPayload payload, Long userId) {
        Game game = gameService.getGame(payload.gameId());
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found."));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new EntityNotFoundException("Cart not found."));
        boolean isCartItem = cart.getItems().stream().anyMatch(c -> c.getGame().getId().equals(game.getId()));
        boolean isPurchasedCartItem = userRepository.existsByIdAndPurchasedGames_Id(userId, game.getId());

        if(isPurchasedCartItem) {
            throw new IllegalStateException("Game already in purchased.");
        }
        if(isCartItem) {
            throw new IllegalStateException("Game already in cart.");
        }
        CartItem cartItem = CartItem.builder().game(game).cart(cart).build();
        cart.getItems().add(cartItem);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void removeCartItem(CartItemPayload payload, Long userId) {
        Game game = gameService.getGame(payload.gameId());
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found."));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new EntityNotFoundException("Cart not found."));
        CartItem cartItem = cart.getItems().stream().filter(item -> item.getGame().getId().equals(game.getId()))
                .findFirst().orElseThrow(() -> new EntityNotFoundException("CartItem not found."));
        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long itemId) {
        return cartItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found."));
    }
}
