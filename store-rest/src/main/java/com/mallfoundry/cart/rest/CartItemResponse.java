package com.mallfoundry.cart.rest;

import com.mallfoundry.cart.CartItem;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class CartItemResponse {
    private final String id;
    private final String storeId;
    private final String productId;
    private final String variantId;
    private final int quantity;
    private final List<String> optionValues;
    private final String name;
    private final String imageUrl;
    private final Date addedTime;

    public CartItemResponse(CartItem item) {
        this.id = item.getId();
        this.storeId = item.getStoreId();
        this.productId = item.getProductId();
        this.variantId = item.getVariantId();
        this.quantity = item.getQuantity();
        this.optionValues = item.getOptionValues();
        this.name = item.getName();
        this.imageUrl = item.getImageUrl();
        this.addedTime = item.getAddedTime();
    }
}
