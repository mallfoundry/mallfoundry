package com.mallfoundry.cart.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mallfoundry.cart.Cart;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CartResponse {

    @JsonIgnore
    private final Cart cart;

    @JsonIgnore
    private final List<CartItemResponse> items;

    public CartResponse(Cart cart) {
        this.cart = cart;
        this.items = Objects.isNull(cart.getItems()) ? Collections.emptyList() :
                cart.getItems().stream().map(CartItemResponse::new).collect(Collectors.toList());
    }

    public String getId() {
        return this.cart.getId();
    }

    public List<CartItemResponse> getItems() {
        return this.items;
    }
}
