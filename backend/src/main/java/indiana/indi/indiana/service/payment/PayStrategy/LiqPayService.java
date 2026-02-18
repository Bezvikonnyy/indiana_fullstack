package indiana.indi.indiana.service.payment.PayStrategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import indiana.indi.indiana.dto.cartAndPay.*;
import indiana.indi.indiana.dtoInterface.cartAndPay.OrderItemDtoInter;
import indiana.indi.indiana.entity.cartAndPay.Order;
import indiana.indi.indiana.entity.cartAndPay.Payment;
import indiana.indi.indiana.entity.games.Game;
import indiana.indi.indiana.entity.games.GameDiscount;
import indiana.indi.indiana.entity.manyToManyEntities.UserPurchasedGames;
import indiana.indi.indiana.enums.OrderStatus;
import indiana.indi.indiana.enums.PaymentMethod;
import indiana.indi.indiana.enums.PaymentStatus;
import indiana.indi.indiana.repository.cartAndPay.OrderRepository;
import indiana.indi.indiana.repository.cartAndPay.PaymentRepository;
import indiana.indi.indiana.repository.games.GameDiscountRepository;
import indiana.indi.indiana.repository.games.GameRepository;
import indiana.indi.indiana.repository.manyToMany.UserPurchasedGamesRepository;
import indiana.indi.indiana.repository.users.UserRepository;
import indiana.indi.indiana.service.cart.CRUDCartServiceImpl;
import indiana.indi.indiana.service.order.CRUDOrderServiceImpl;
import indiana.indi.indiana.service.payment.PaymentStrategy;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service("LIQPAY")
@RequiredArgsConstructor
public class LiqPayService implements PaymentStrategy {

    @Value("${liqpay.public-key}")
    private String publicKey;
    @Value("${liqpay.private-key}")
    private String privateKey;
    private final JdbcTemplate jdbcTemplate;
    private final CRUDCartServiceImpl cartService;
    private final CRUDOrderServiceImpl orderService;
    private final ObjectMapper objectMapper;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final GameRepository gameRepository;
    private final GameDiscountRepository gameDiscountRepository;
    private final UserRepository userRepository;
    private final UserPurchasedGamesRepository purchasedGamesRepository;

    @Transactional
    @Override
    public PaymentRequestDto createPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        Payment payment = Payment.builder()
                .order(order)
                .status(PaymentStatus.PENDING)
                .paymentMethod(PaymentMethod.LIQPAY)
                .amount(order.getTotalAmount())
                .build();
        paymentRepository.save(payment);

        String data = generateData(order);
        String signature = generateSignature(data);

        return new PaymentRequestDto(data, signature);
    }

    private String generateData(Order order) {
        try {
            StringDataDto params = new StringDataDto(
                    publicKey,
                    3,
                    "pay",
                    order.getTotalAmount().toPlainString(),
                    "UAH",
                    "Оплата " + order.getItems().size() + " игр",
                    order.getId().toString(),
                    1,
                    "https://78ed-212-79-122-68.ngrok-free.app/api/cart/callback/LIQPAY",
                    "http://localhost:5173/cart"
            );

            String json = objectMapper.writeValueAsString(params);
            return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize LiqPay data", e);
        }
    }

    private String generateSignature(String data) {
        try{
            String signStr = privateKey + data + privateKey;
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] sha1 = digest.digest(signStr.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(sha1);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 algorithm no available", e);
        }
    }

    @Transactional
    @Override
    public void processCallback(String data, String signature) {
        verifySignature(data,signature);

        String json = new String(Base64.getDecoder().decode(data), StandardCharsets.UTF_8);
        LiqPayCallbackDto callbackDto;
        try {
            callbackDto = objectMapper.readValue(json, LiqPayCallbackDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Long orderId = Long.parseLong(callbackDto.orderId());
        OrderDto order = orderService.getOrder(orderId);
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found."));

        if (payment.getStatus() == PaymentStatus.PENDING) {
            switch (callbackDto.status()) {
                case "sandbox", "success" -> {
                    paymentRepository.updatePayment(payment.getId(), PaymentStatus.SUCCESS);
                    orderService.updateOrder(orderId, OrderStatus.PAID);
                    cartService.clearCart(order.userId());

                    purchasedGame(orderId, order.userId());
                }

                case "failure", "reversed" -> {
                    paymentRepository.updatePayment(payment.getId(), PaymentStatus.CANCELLED);
                    orderRepository.updateOrder(orderId, OrderStatus.CANCELLED);
                }

                default -> paymentRepository.updatePayment(payment.getId(), PaymentStatus.PENDING);
            }
        }
    }

    private BigDecimal calculateFinalPrice(Game game) {
        // Получаем активную скидку, если есть
        GameDiscount discount = gameDiscountRepository.findActiveDiscountByGame(
                game.getId(), LocalDateTime.now()
        );

        if (discount != null) {
            // Цена со скидкой
            return game.getPrice()
                    .subtract(game.getPrice()
                            .multiply(BigDecimal.valueOf(discount.getDiscountPercent()))
                            .divide(BigDecimal.valueOf(100)));
        }

        // Если скидки нет — обычная цена
        return game.getPrice();
    }

    @Transactional
    private void purchasedGame(Long orderId, Long userId) {
        List<OrderItemDtoInter> orderItems = orderRepository.getOrderItemByOrderId(orderId);

        for (OrderItemDtoInter item : orderItems) {
            Game game = gameRepository.findById(item.getGameId())
                    .orElseThrow(() -> new EntityNotFoundException("Game not found"));

            // Рассчитать финальную цену с учётом активной скидки
            BigDecimal finalPrice = calculateFinalPrice(game);

            // Сохранить покупку
            UserPurchasedGames purchase = new UserPurchasedGames();
            purchase.setUser(userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found")));
            purchase.setGame(game);
            purchase.setPriceAtMoment(finalPrice);
            purchasedGamesRepository.save(purchase);

            // Увеличить счётчик покупок
            game.setPurchasesCount(game.getPurchasesCount() + 1);
            gameRepository.save(game);
        }
    }


    private void verifySignature(String data, String signature) {
        if(data == null || data.isBlank()) {
            throw new SecurityException("Data is empty.");
        }
        if(signature == null || signature.isBlank()) {
            throw new SecurityException("Signature is empty.");
        }

        String expected = generateSignature(data);
        if(!MessageDigest.isEqual(expected.getBytes(StandardCharsets.UTF_8),
                                    signature.getBytes(StandardCharsets.UTF_8))) {
            throw new SecurityException("Invalid signature");
        }
    }
}
