package org.mallfoundry.rest.cart;

import lombok.Getter;
import org.mallfoundry.cart.CartItem;
import org.mallfoundry.catalog.OptionSelection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
public class CartItemResponse extends CartItemRequest {
    private final String id;
    private final String storeId;
    private final BigDecimal price;
    private final List<OptionSelection> optionSelections;
    private final Date addedTime;

    public CartItemResponse(CartItem item) {
        this.id = item.getId();
        this.storeId = item.getStoreId();
        this.price = item.getPrice();
        this.optionSelections = item.getOptionSelections();
        this.addedTime = item.getAddedTime();
        this.setProductId(item.getProductId());
        this.setVariantId(item.getVariantId());
        this.setQuantity(item.getQuantity());
        this.setName(item.getName());
        this.setImageUrl(item.getImageUrl());
        this.setChecked(item.isChecked());
    }
}
