package org.mallfoundry.rest.cart;

import lombok.Getter;
import org.mallfoundry.cart.CartItem;
import org.mallfoundry.catalog.OptionSelection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
public class CartItemResponse {
    private final String id;
    private final String storeId;
    private final String productId;
    private final String variantId;
    private final int quantity;
    private final BigDecimal price;
    private final List<OptionSelection> optionSelections;
    private final String name;
    private final String imageUrl;
    private final Date addedTime;

    public CartItemResponse(CartItem item) {
        this.id = item.getId();
        this.storeId = item.getStoreId();
        this.productId = item.getProductId();
        this.variantId = item.getVariantId();
        this.quantity = item.getQuantity();
        this.price = item.getPrice();
        this.optionSelections = item.getOptionSelections();
        this.name = item.getName();
        this.imageUrl = item.getImageUrl();
        this.addedTime = item.getAddedTime();
    }
}
