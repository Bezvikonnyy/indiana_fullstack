package indiana.indi.indiana.service.cart;

import indiana.indi.indiana.controller.payload.CartItemPayload;
import indiana.indi.indiana.entity.cartAndPay.Cart;
import indiana.indi.indiana.entity.cartAndPay.CartItem;
import indiana.indi.indiana.entity.games.Game;
import indiana.indi.indiana.repository.cartAndPay.CartItemRepository;
import indiana.indi.indiana.repository.cartAndPay.CartRepository;
import indiana.indi.indiana.repository.games.GameRepository;
import indiana.indi.indiana.repository.users.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CRUDCartItemServiceImpl implements CRUDCartItemService{

    private final CartItemRepository cartItemRepository;

    private final GameRepository gameRepository;

    private final CartRepository cartRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addCartItem(CartItemPayload payload, Long userId) {
        boolean isCartItem = cartRepository.existsByCartIdAndGameId(userId, payload.gameId());
        boolean isPurchasedCartItem = userRepository.existsByUserIdAndGameId(userId, payload.gameId());

        if(isCartItem) {
            throw new IllegalStateException("Game already in cart.");
        }
        if(isPurchasedCartItem) {
            throw new IllegalStateException("Game already in purchased.");
        }

        Game game = gameRepository.getReferenceById(payload.gameId());
        Cart cart = cartRepository.getReferenceById(userId);

        CartItem cartItem = CartItem.builder().game(game).cart(cart).build();
        cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public void removeCartItem(CartItemPayload payload, Long userId) {
        cartRepository.removeCartItemByUserAndGame(userId, payload.gameId());
    }
}
