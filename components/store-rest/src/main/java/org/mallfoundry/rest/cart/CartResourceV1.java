package org.mallfoundry.rest.cart;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class CartResourceV1 {

    private final CartRestService cartRestService;

    public CartResourceV1(CartRestService cartRestService) {
        this.cartRestService = cartRestService;
    }

    @PostMapping("/carts")
    public CartResponse createCart(@RequestBody CreateCartRequest request) {
        return this.cartRestService.createCart(request);
    }

    @DeleteMapping("/carts/{id}")
    public void deleteCart(@PathVariable("id") String id) {
        this.cartRestService.deleteCart(id);
    }

    @PostMapping("/carts/{id}/items")
    public CartItemResponse addCartItem(@PathVariable("id") String id, @RequestBody CartItemRequest request) {
        return this.cartRestService.addCartItem(id, request);
    }

    @DeleteMapping("/carts/{id}/items/{item_id}")
    public void removeCartItem(@PathVariable("id") String id, @PathVariable("item_id") String itemId) {
        this.cartRestService.removeCartItem(id, itemId);
    }

    @DeleteMapping("/carts/{id}/items/batch")
    public void removeCartItems(@PathVariable("id") String id, @RequestBody List<String> itemIds) {
        this.cartRestService.removeCartItems(id, itemIds);
    }

    @PatchMapping("/carts/{id}/items/{item_id}")
    public void setCartItem(@PathVariable("token") String id, @PathVariable("item_id") String itemId, @RequestBody CartItemRequest request) {
        this.cartRestService.setCartItem(id, itemId, request);
    }

    @GetMapping("/carts/{id}")
    public Optional<CartResponse> getCart(@PathVariable("id") String id) {
        return this.cartRestService.getCart(id);
    }
}
