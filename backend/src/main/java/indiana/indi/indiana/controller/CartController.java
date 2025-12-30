package indiana.indi.indiana.controller;

import indiana.indi.indiana.controller.payload.CartItemPayload;
import indiana.indi.indiana.dto.cartAndPay.*;
import indiana.indi.indiana.service.cart.CartForControllerService;
import indiana.indi.indiana.service.user.customUser.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartForControllerService service;

    @GetMapping("/my")
    public CartDto getCart(@AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user != null ? user.getId() : null;
        return service.getCart(userId);
    }

    @PutMapping("/add")
    public CartDto addCartItem(@AuthenticationPrincipal CustomUserDetails user,
                               @RequestBody CartItemPayload payload) {
        return service.addCartItem(payload, user.getId());
    }

    @PutMapping("/remove")
    public CartDto removeCartItem(@AuthenticationPrincipal CustomUserDetails user,
                                  @RequestBody CartItemPayload payload) {
        return service.removeCartItem(payload, user.getId());
    }

    @PutMapping("/clear")
    public CartDto clearCart(@AuthenticationPrincipal CustomUserDetails user) {
        return service.clearCart(user.getId());
    }

    @PutMapping("/order")
    public OrderDto toOrder(@AuthenticationPrincipal CustomUserDetails user) {
        return service.toOrder(user.getId());
    }

    @PostMapping("/checkout/{paymentMethod}/{orderId}")
    public PaymentRequestDto checkout(@PathVariable String paymentMethod,
                                      @PathVariable Long orderId) {
        return service.createPayment(paymentMethod, orderId);
    }

    @PostMapping("/callback/{paymentMethod}")
    public void payment(@PathVariable String paymentMethod,
                        @RequestParam("data") String data,
                        @RequestParam("signature") String signature) {
        service.callbackPayment(paymentMethod, data, signature);
    }

    @GetMapping("/order/{orderId}/result")
    public OrderStatusDto getStatusPayment(@PathVariable Long orderId) {
        return service.getOrderStatus(orderId);
    }
}
