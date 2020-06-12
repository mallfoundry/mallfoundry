package org.mallfoundry.rest.cart;

import org.mallfoundry.cart.CartService;
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
import java.util.Set;

@RestController
@RequestMapping("/v1")
public class CartResourceV1 {

    private final CartRestService cartRestService;

    private final CartService cartService;

    public CartResourceV1(CartRestService cartRestService, CartService cartService) {
        this.cartRestService = cartRestService;
        this.cartService = cartService;
    }

    @PostMapping("/carts")
    public CartResponse createCart(@RequestBody CreateCartRequest request) {
        return this.cartRestService.createCart(request);
    }

    @PostMapping("/carts/{id}/items")
    public CartItemResponse addCartItem(@PathVariable("id") String id, @RequestBody CartItemRequest request) {
        return this.cartRestService.addCartItem(id, request);
    }

    @PostMapping("/carts/{id}/items/{item_id}/quantity/adjust")
    public void adjustCartItemQuantity(@PathVariable("id") String id,
                                       @PathVariable("item_id") String itemId,
                                       @RequestBody int quantityDelta) {
        this.cartService.adjustCartItemQuantity(id, itemId, quantityDelta);
    }

    @PostMapping("/carts/{id}/items/{item_id}/check")
    public void checkCartItem(@PathVariable("id") String id, @PathVariable("item_id") String itemId) {
        this.cartService.checkCartItem(id, itemId);
    }

    @PostMapping("/carts/{id}/items/{item_id}/uncheck")
    public void uncheckCartItem(@PathVariable("id") String id, @PathVariable("item_id") String itemId) {
        this.cartService.uncheckCartItem(id, itemId);
    }

    @PostMapping("/carts/{id}/items/check/batch")
    public void checkCartItems(@PathVariable("id") String id, @RequestBody Set<String> itemIds) {
        this.cartService.checkCartItems(id, itemIds);
    }

    @PostMapping("/carts/{id}/items/uncheck/batch")
    public void uncheckCartItems(@PathVariable("id") String id, @RequestBody Set<String> itemIds) {
        this.cartService.uncheckCartItems(id, itemIds);
    }

    @PostMapping("/carts/{id}/items/check")
    public void checkAllCartItems(@PathVariable("id") String id) {
        this.cartService.checkAllCartItems(id);
    }

    @PostMapping("/carts/{id}/items/uncheck")
    public void uncheckAllCartItems(@PathVariable("id") String id) {
        this.cartService.uncheckAllCartItems(id);
    }

    @GetMapping("/carts/{id}")
    public Optional<CartResponse> getCart(@PathVariable("id") String id) {
        return this.cartRestService.getCart(id);
    }

    @PatchMapping("/carts/{id}/items/{item_id}")
    public void updateCartItem(@PathVariable("id") String id, @PathVariable("item_id") String itemId,
                               @RequestBody CartItemRequest request) {
        this.cartService.updateCartItem(id,
                request.assignCartItem(this.cartService.createCart(id).createItem(itemId)));
    }

    @DeleteMapping("/carts/{id}")
    public void deleteCart(@PathVariable("id") String id) {
        this.cartService.deleteCart(id);
    }

    @DeleteMapping("/carts/{id}/items/{item_id}")
    public void removeCartItem(@PathVariable("id") String id, @PathVariable("item_id") String itemId) {
        this.cartService.removeCartItem(id, itemId);
    }

    @DeleteMapping("/carts/{id}/items/batch")
    public void removeCartItems(@PathVariable("id") String id, @RequestBody List<String> itemIds) {
        this.cartService.removeCartItems(id, itemIds);
    }
}
