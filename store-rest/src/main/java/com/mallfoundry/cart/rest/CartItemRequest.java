package com.mallfoundry.cart.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {

    private String productId;

    private String variantId;

    private int quantity;

    private String name;

    private String imageUrl;
}
