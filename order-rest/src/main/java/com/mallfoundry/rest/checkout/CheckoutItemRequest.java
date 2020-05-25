package com.mallfoundry.rest.checkout;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutItemRequest {

    private String productId;

    private String variantId;

    private int quantity;
}
