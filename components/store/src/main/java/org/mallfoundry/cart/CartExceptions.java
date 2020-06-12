package org.mallfoundry.cart;

import java.util.function.Supplier;

public abstract class CartExceptions {

    public static Supplier<CartException> notExist() {
        return () -> new CartException("The CART object does not exist");
    }

    public static Supplier<CartException> itemNotExist() {
        return () -> new CartException("The cart item object does not exist");
    }
}
