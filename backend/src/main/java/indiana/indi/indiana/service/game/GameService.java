package indiana.indi.indiana.service.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class GameService {

    public BigDecimal finalPrice(BigDecimal price, Integer discount) {
        BigDecimal discountPercent =
                discount != null
                        ? BigDecimal.valueOf(discount) : BigDecimal.ZERO;

        BigDecimal finalPrice;
        if (discountPercent.compareTo(BigDecimal.ZERO) == 0) {
            finalPrice = price;
        } else {
            finalPrice = price.multiply(BigDecimal.ONE.subtract(discountPercent.divide(BigDecimal.valueOf(100))));
        }
        return finalPrice;
    }
}
