package indiana.indi.indiana.service.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import indiana.indi.indiana.dto.LiqPayCallbackDto;
import indiana.indi.indiana.dto.OrderDto;
import indiana.indi.indiana.dto.OrderStatusDto;
import indiana.indi.indiana.dto.PaymentRequestDto;
import indiana.indi.indiana.entity.Order;
import indiana.indi.indiana.entity.Payment;
import indiana.indi.indiana.enums.OrderStatus;
import indiana.indi.indiana.enums.PaymentMethod;
import indiana.indi.indiana.enums.PaymentStatus;
import indiana.indi.indiana.repository.OrderRepository;
import indiana.indi.indiana.repository.PaymentRepository;
import indiana.indi.indiana.service.cart.CRUDCartServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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
        params.put("server_url", "https://53646f1eb5b2.ngrok-free.app/api/cart/liqpay/result");
        params.put("result_url", "http://localhost:3000/order/" + order.id() + "/result");


        String json = objectMapper.writeValueAsString(params);
        String data = Base64.encodeBase64String(json.getBytes("UTF-8"));
        String signature = generateSignature(data);

        return new PaymentRequestDto(data, signature);
    }

    private String generateSignature(String data) {
        String signStr = privateKey + data + privateKey;
        byte[] sha1 = DigestUtils.sha1(signStr);
        return Base64.encodeBase64String(sha1);
    }

    public void processCallback(String data, String signature) throws Exception {
        System.out.println("LiqPay callback received: data=" + data + ", signature=" + signature);
        if(!generateSignature(data).equals(signature)) {
            System.out.println("Invalid signature!");
            throw new SecurityException("Invalid signature.");
        }
        String json = new String(Base64.decodeBase64(data), StandardCharsets.UTF_8);
        LiqPayCallbackDto callbackDto = objectMapper.readValue(json, LiqPayCallbackDto.class);

        Long order_id = Long.parseLong(callbackDto.order_id());
        Order order = orderRepository.findById(order_id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found."));

        switch (callbackDto.status()) {
            case "sandbox", "success" -> {
                payment.setStatus(PaymentStatus.SUCCESS);
                order.setStatus(OrderStatus.PAID);
                cartService.clearCart(order.getUser().getId());
            }
            case "failure", "reversed" -> {
                payment.setStatus(PaymentStatus.CANCELLED);
                order.setStatus(OrderStatus.CANCELLED);
            }
            default -> {
                payment.setStatus(PaymentStatus.PENDING);
            }
        }
        paymentRepository.save(payment);
        orderRepository.save(order);
    }

    public OrderStatusDto getOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found."));
        return new OrderStatusDto(order.getId(), order.getStatus().toString());
    }
}
