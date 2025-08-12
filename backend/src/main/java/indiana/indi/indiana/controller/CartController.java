package indiana.indi.indiana.controller;

import indiana.indi.indiana.controller.payload.CartItemPayload;
import indiana.indi.indiana.controller.payload.NewOrderPayload;
import indiana.indi.indiana.dto.CartDto;
import indiana.indi.indiana.dto.OrderDto;
import indiana.indi.indiana.dto.OrderStatusDto;
import indiana.indi.indiana.dto.PaymentRequestDto;
import indiana.indi.indiana.service.cart.CartForControllerService;
import indiana.indi.indiana.service.order.LiqPayService;
import indiana.indi.indiana.service.user.CustomUserDetails;
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
        return service.addCartItem(user.getId(), payload);
    }

    @PutMapping("/remove")
    public CartDto removeCartItem(@AuthenticationPrincipal CustomUserDetails user,
                                  @RequestBody CartItemPayload payload) {
        return service.removeCartItem(user.getId(), payload);
    }

    @PutMapping("/clear")
    public CartDto clearCart(@AuthenticationPrincipal CustomUserDetails user) {
        return service.clearCart(user.getId());
    }

    @PutMapping("/order")
    public OrderDto toOrder(@AuthenticationPrincipal CustomUserDetails user,
                            @RequestBody NewOrderPayload payload) {
        return service.toOrder(user.getUser(), payload);
    }

    @PostMapping("/checkout")
    public PaymentRequestDto checkout(@AuthenticationPrincipal CustomUserDetails user,
                                      @RequestBody NewOrderPayload payload) throws Exception {
        OrderDto orderDto = service.toOrder(user.getUser(), payload);
        return liqPayService.createPayment(orderDto);
    }

    @PostMapping("/liqpay/result")
    public void payment(@RequestParam("data") String data, @RequestParam("signature") String signature)
            throws Exception {liqPayService.processCallback(data, signature);
    }

    @GetMapping("/order/{orderId}/result")
    public OrderStatusDto getStatusPayment(@PathVariable Long orderId) {
        return liqPayService.getOrderStatus(orderId);
    }
}
