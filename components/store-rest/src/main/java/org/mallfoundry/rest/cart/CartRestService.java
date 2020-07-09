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

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.cart.CartException;
import org.mallfoundry.cart.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartRestService {

    private final CartService cartService;

    public CartRestService(CartService cartService) {
        this.cartService = cartService;
    }

//    private CartItem createCartItemFromRequest(String cartId, CartItemRequest request) {
//        return this.cartService.createCartItem().toBuilder()
//                .productId(request.getProductId())
//                .variantId(request.getVariantId())
//                .quantity(request.getQuantity())
//                .name(request.getName())
//                .imageUrl(request.getImageUrl())
//                .build();
//    }

    public CartResponse createCart(CreateCartRequest request) {
        if (CollectionUtils.isEmpty(request.getItems())) {
            throw new CartException("The cart items must not be empty");
        }
        var cart = this.cartService.createCart(request.getId());
        request.getItems().stream()
                .map(itemRequest -> itemRequest.assignCartItem(cart.createItem(null)))
                .forEach(cart::addItem);
        return new CartResponse(this.cartService.createCart(cart));
    }

    @Transactional
    public Optional<CartResponse> getCart(String id) {
        return this.cartService.getCart(id).map(CartResponse::new);
    }
}
