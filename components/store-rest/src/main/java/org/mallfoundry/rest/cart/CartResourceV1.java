/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
import java.util.stream.Collectors;

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
    public Optional<CartItemResponse> addCartItem(@PathVariable("id") String id, @RequestBody CartItemRequest request) {
        return Optional.of(
                this.cartService.addCartItem(id,
                        request.assignCartItem(this.cartService.createCart((String) null).createItem(null))))
                .map(CartItemResponse::new);
    }

    @PostMapping("/carts/{id}/items/batch")
    public List<CartItemResponse> addCartItems(@PathVariable("id") String id,
                                               @RequestBody List<CartItemRequest> requests) {
        var items = requests.stream()
                .map(request ->
                        request.assignCartItem(this.cartService.createCart((String) null).createItem(null)))
                .collect(Collectors.toList());
        return this.cartService.addCartItems(id, items)
                .stream().map(CartItemResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @PostMapping("/carts/{id}/items/{item_id}/adjust")
    public void adjustCartItem(@PathVariable("id") String id,
                               @PathVariable("item_id") String itemId,
                               @RequestBody CartItemAdjustItemIdRequest request) {
        this.cartService.adjustCartItem(id,
                this.cartService.createCart((String) null)
                        .createItemAdjustment(itemId).toBuilder()
                        .quantityDelta(request.getQuantityDelta()).build());
    }


    @PostMapping("/carts/{id}/items/adjust")
    public void adjustCartItem(@PathVariable("id") String id,
                               @RequestBody CartItemAdjustProductRequest request) {
        this.cartService.adjustCartItem(id,
                this.cartService.createCart((String) null)
                        .createItemAdjustment(null).toBuilder()
                        .productId(request.getProductId()).variantId(request.getVariantId())
                        .quantityDelta(request.getQuantityDelta()).build());
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
