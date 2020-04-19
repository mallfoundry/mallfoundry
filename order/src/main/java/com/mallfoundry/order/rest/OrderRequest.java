package com.mallfoundry.order.rest;

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

    @Getter
    @Setter
    public static class OrderItemRequest {
        private String id;
        @JsonProperty("discount_amount")
        private BigDecimal discountAmount;
    }

    public boolean isDiscountAmountsChanged() {
        return MapUtils.isNotEmpty(this.getDiscountAmounts());
    }

    public Map<String, BigDecimal> getDiscountAmounts() {
        return Objects.isNull(this.getItems()) ? Collections.emptyMap() :
                this.getItems().stream()
                        .collect(Collectors.toMap(OrderItemRequest::getId,
                                OrderItemRequest::getDiscountAmount));
    }
}
