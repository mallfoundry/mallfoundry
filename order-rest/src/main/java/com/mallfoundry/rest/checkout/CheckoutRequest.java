package com.mallfoundry.rest.checkout;

import com.mallfoundry.checkout.Checkout;
import com.mallfoundry.rest.shipping.AddressRequest;
import com.mallfoundry.shipping.DefaultAddress;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class CheckoutRequest {

    private String cartId;

    private AddressRequest shippingAddress;

    private List<CheckoutItemRequest> items;

    public void assignToCheckout(Checkout checkout) {
        if (Objects.nonNull(this.shippingAddress)) {
            var address = new DefaultAddress();
            this.shippingAddress.assignToAddress(address);
            checkout.setShippingAddress(address);
        }
        this.getItems()
                .stream()
                .map(item ->
                        checkout.createItem()
                                .toBuilder()
                                .productId(item.getProductId())
                                .variantId(item.getVariantId())
                                .quantity(item.getQuantity())
                                .build())
                .forEach(checkout::addItem);
    }
}
