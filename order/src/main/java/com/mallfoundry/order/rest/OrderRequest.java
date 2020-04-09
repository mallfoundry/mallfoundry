package com.mallfoundry.order.rest;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private AddressRequest billingAddress;
    private AddressRequest shippingAddress;
    private List<OrderItemRequest> items;

    @Getter
    @Setter
    static class AddressRequest {
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
