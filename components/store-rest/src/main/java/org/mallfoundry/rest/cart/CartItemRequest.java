package org.mallfoundry.rest.cart;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.cart.CartItem;

@Getter
@Setter
public class CartItemRequest {

    private String productId;

    private String variantId;

    private int quantity;

    private String name;

    private String imageUrl;

    private boolean checked;

    public CartItem assignCartItem(CartItem item) {
        return item.toBuilder()
                .productId(this.productId)
                .variantId(this.variantId)
                .quantity(this.quantity)
                .name(this.name)
                .imageUrl(this.imageUrl)
                .checked(this.checked)
                .build();
    }
}
