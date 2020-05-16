package com.mallfoundry.cart;

public class CartTokenService {

    private final CartScope cartScope;

    public CartTokenService(CartScope cartScope) {
        this.cartScope = cartScope;
    }

    public String getCartId(String token) {
        return "";
    }
}
