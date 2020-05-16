package com.mallfoundry.cart.rest;

import com.mallfoundry.cart.CartItem;

import java.util.Date;
import java.util.List;

public class CartItemResponse {

    private final CartItem item;

    public CartItemResponse(CartItem item) {
        this.item = item;
    }

    public String getId() {
        return this.item.getId();
    }

    public String getStoreId() {
        return this.item.getStoreId();
    }

    public String getProductId() {
        return this.item.getProductId();
    }

    public String getVariantId() {
        return this.item.getVariantId();
    }

    public String getName() {
        return this.item.getName();
    }

    public String getImageUrl() {
        return this.item.getImageUrl();
    }

    public List<String> getOptionValues() {
        return this.item.getOptionValues();
    }

    public int getQuantity() {
        return this.item.getQuantity();
    }

    public Date getAddedTime() {
        return this.item.getAddedTime();
    }
}
