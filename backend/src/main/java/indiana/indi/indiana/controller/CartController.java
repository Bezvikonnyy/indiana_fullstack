package indiana.indi.indiana.controller;

import indiana.indi.indiana.controller.payload.CartItemPayload;
import indiana.indi.indiana.dto.cartAndPay.*;
import indiana.indi.indiana.service.cart.CartForControllerService;
import indiana.indi.indiana.service.payment.LigPayStrategy.LiqPayService;
import indiana.indi.indiana.service.user.customUser.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartForControllerService service;

    private final LiqPayService liqPayService;

    @GetMapping("/my")
    public CartDto getCart(@AuthenticationPrincipal CustomUserDetails user){
        return service.getCart(user.getId());
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

    @GetMapping("/order/{orderId}")
    public OrderDto paymentMethod(@PathVariable Long orderId) {
        return service.paymentMethod(orderId);
    }

//    @PostMapping("/checkout")
//    public PaymentRequestDto checkout(@RequestBody PaymentMethodDto payment) throws Exception {
//        return liqPayService.createPayment(payment);
//    }

    @PostMapping("/liqpay/result")
    public void payment(@RequestParam("data") String data,
                        @RequestParam("signature") String signature)
            throws Exception {liqPayService.processCallback(data, signature);
    }

    @GetMapping("/order/{orderId}/result")
    public OrderStatusDto getStatusPayment(@PathVariable Long orderId) {
        return liqPayService.getOrderStatus(orderId);
    }
}
