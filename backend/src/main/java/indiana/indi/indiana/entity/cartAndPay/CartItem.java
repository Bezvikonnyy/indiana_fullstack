package indiana.indi.indiana.entity.cartAndPay;

import indiana.indi.indiana.entity.games.Game;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id",nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "cart_id",nullable = false)
    private Cart cart;
}
