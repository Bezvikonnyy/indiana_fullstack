package indiana.indi.indiana.mapper;

import indiana.indi.indiana.dto.CardItemDto;
import indiana.indi.indiana.entity.Cart;
import indiana.indi.indiana.entity.Game;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.repository.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameMapper {

    private final CartRepository cartRepository;

    public CardItemDto toDto(Game game, User user) {
        boolean isFavorite = false;
        boolean isInCart = false;
        boolean isPurchased = false;

        if (user == null) {
            isFavorite = false;
            isInCart = false;
            isPurchased = false;
        } else {
            Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new EntityNotFoundException("Cart not found."));
            isFavorite = game.getFavorites().contains(user);
            isInCart = cart.getItems().contains(game);
            isPurchased = game.getBuyers().contains(user);
        }
        return new CardItemDto(
                game.getId(),
                game.getTitle(),
                game.getImageUrl(),
                game.getPrice(),
                isFavorite,
                isInCart,
                isPurchased);
    }
}
