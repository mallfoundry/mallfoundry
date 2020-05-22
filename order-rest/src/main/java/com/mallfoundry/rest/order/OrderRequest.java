package com.mallfoundry.rest.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.MapUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderRequest {

    @JsonProperty("staff_notes")
    private String staffNotes;

    @JsonProperty("discount_amount")
    private BigDecimal discountAmount;

    private List<OrderItemRequest> items;

    public boolean isDiscountAmountsChanged() {
        return MapUtils.isNotEmpty(this.getDiscountAmounts());
    }

    public Map<String, BigDecimal> getDiscountAmounts() {
        return Objects.isNull(this.getItems()) ? Collections.emptyMap() :
                this.getItems().stream()
                        .filter(request -> Objects.nonNull(request.getDiscountAmount()))
                        .collect(Collectors.toMap(OrderItemRequest::getId,
                                OrderItemRequest::getDiscountAmount));
    }

    public boolean isDiscountShippingCostsChanged() {
        return MapUtils.isNotEmpty(this.getDiscountShippingCosts());
    }

    public Map<String, BigDecimal> getDiscountShippingCosts() {
        return Objects.isNull(this.getItems()) ? Collections.emptyMap() :
                this.getItems().stream()
                        .filter(request -> Objects.nonNull(request.getDiscountShippingCost()))
                        .collect(Collectors.toMap(OrderItemRequest::getId,
                                OrderItemRequest::getDiscountShippingCost));
    }

    @Getter
    @Setter
    public static class OrderItemRequest {
        private String id;
        @JsonProperty("discount_amount")
        private BigDecimal discountAmount;
        @JsonProperty("discount_shipping_cost")
        private BigDecimal discountShippingCost;
    }

    @Getter
    @Setter
    public static class CancelRequest {
        private String reason;
    }
}
