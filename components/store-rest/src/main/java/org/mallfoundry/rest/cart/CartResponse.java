package org.mallfoundry.rest.cart;

import lombok.Getter;
import org.mallfoundry.cart.Cart;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class CartResponse {

    private final String id;

    private final String customerId;

    private final List<CartItemResponse> items;

    public CartResponse(Cart cart) {
        this.id = cart.getId();
        this.customerId = cart.getCustomerId();
        this.items = Objects.isNull(cart.getItems())
                ? Collections.emptyList()
                : cart.getItems().stream().map(CartItemResponse::new).collect(Collectors.toList());
    }
}