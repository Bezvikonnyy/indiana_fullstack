package indiana.indi.indiana.service.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import indiana.indi.indiana.dto.LiqPayCallbackDto;
import indiana.indi.indiana.dto.OrderDto;
import indiana.indi.indiana.dto.OrderStatusDto;
import indiana.indi.indiana.dto.PaymentRequestDto;
import indiana.indi.indiana.entity.*;
import indiana.indi.indiana.enums.OrderStatus;
import indiana.indi.indiana.enums.PaymentMethod;
import indiana.indi.indiana.enums.PaymentStatus;
import indiana.indi.indiana.repository.OrderRepository;
import indiana.indi.indiana.repository.PaymentRepository;
import indiana.indi.indiana.repository.UserRepository;
import indiana.indi.indiana.service.cart.CRUDCartServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LiqPayService {

    @Value("${liqpay.public-key}")
    private String publicKey;

    @Value("${liqpay.private-key}")
    private String privateKey;

    private final ObjectMapper objectMapper;

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    private final CRUDCartServiceImpl cartService;

    private final UserRepository userRepository;

    @Transactional
    public PaymentRequestDto createPayment(OrderDto order) throws Exception {
        Payment payment = Payment.builder()
                .order(orderRepository.findById(order.id())
                        .orElseThrow(() -> new EntityNotFoundException("Order not found")))
                .status(PaymentStatus.PENDING)
                .paymentMethod(PaymentMethod.LIQPAY)
                .amount(order.totalPrice())
                .build();
        paymentRepository.save(payment);

        Map<String, Object> params = new HashMap<>();
        params.put("version", "3");
        params.put("public_key", publicKey);
        params.put("action", "pay");
        params.put("amount", order.totalPrice());
        params.put("currency", "UAH");
        params.put("description", order.items().toString());
        params.put("order_id", order.id().toString());
        params.put("sandbox", "1");
        params.put("server_url", "https://95bccf693889.ngrok-free.app/api/cart/liqpay/result");
        params.put("result_url", "http://localhost:3000/order/" + order.id() + "/result");


        String json = objectMapper.writeValueAsString(params);
        String data = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
        String signature = generateSignature(data);

        return new PaymentRequestDto(data, signature);
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
    public void processCallback(String data, String signature) throws Exception {
        if(!generateSignature(data).equals(signature)) {
            throw new SecurityException("Invalid signature.");
        }

        String json = new String(Base64.getDecoder().decode(data), StandardCharsets.UTF_8);
        LiqPayCallbackDto callbackDto = objectMapper.readValue(json, LiqPayCallbackDto.class);

        Long orderId = Long.parseLong(callbackDto.order_id());
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found."));

        switch (callbackDto.status()) {
            case "sandbox", "success" -> {
                payment.setStatus(PaymentStatus.SUCCESS);
                order.setStatus(OrderStatus.PAID);
                cartService.clearCart(order.getUser().getId());

                User user = userRepository.findById(order.getUser().getId())
                        .orElseThrow(() -> new EntityNotFoundException("User not found"));
                user.getPurchasedGames().addAll(gameIds(order));
                userRepository.save(user);
            }

            case "failure", "reversed" -> {
                payment.setStatus(PaymentStatus.CANCELLED);
                order.setStatus(OrderStatus.CANCELLED);
            }

            default -> payment.setStatus(PaymentStatus.PENDING);
        }

        paymentRepository.save(payment);
        orderRepository.save(order);
    }


    @Transactional
    private Set<Game> gameIds(Order order) {
        return new HashSet<>(order.getItems().stream()
                .map(OrderItem::getGame)
                .collect(Collectors.toSet()));
    }

    public OrderStatusDto getOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        return new OrderStatusDto(order.getId(), order.getStatus().toString());
    }
}
