package com.mallfoundry.order.rest;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {
    @JsonProperty("shipping_address")
    private ShippingAddressRequest shippingAddress;
    private List<OrderItemRequest> items;

    @Getter
    @Setter
    static class ShippingAddressRequest {
        private String consignee;
        @JsonProperty("country_code")
        private String countryCode;
        private String mobile;
        @JsonProperty("postal_code")
        private String postalCode;
        private String address;
        private String location;
    }

    @Getter
    @Setter
    static class OrderItemRequest {
        @JsonProperty("product_id")
        private String productId;
        @JsonProperty("variant_id")
        private String variantId;
        private int quantity;
    }
}
