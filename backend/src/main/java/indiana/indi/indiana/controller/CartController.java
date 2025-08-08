package indiana.indi.indiana.controller;

import indiana.indi.indiana.controller.payload.CartItemPayload;
import indiana.indi.indiana.controller.payload.NewOrderPayload;
import indiana.indi.indiana.dto.CartDto;
import indiana.indi.indiana.dto.OrderDto;
import indiana.indi.indiana.service.cart.CartForControllerService;
import indiana.indi.indiana.service.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartForControllerService service;

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
        return service.toOrder(user.getId(), user.getUser(), payload);
    }
}
