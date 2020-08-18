/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.rest.order;

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
        return Objects.isNull(this.getItems())
                ? Collections.emptyMap()
                : this.getItems().stream()
                        .filter(request -> Objects.nonNull(request.getDiscountAmount()))
                        .collect(Collectors.toMap(OrderItemRequest::getId,
                                OrderItemRequest::getDiscountAmount));
    }

    public boolean isDiscountShippingCostsChanged() {
        return MapUtils.isNotEmpty(this.getDiscountShippingCosts());
    }

    public Map<String, BigDecimal> getDiscountShippingCosts() {
        return Objects.isNull(this.getItems())
                ? Collections.emptyMap()
                : this.getItems().stream()
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
        private String cancelReason;
    }

    @Getter
    @Setter
    public static class DeclineRequest {
        private String declineReason;
    }

    @Getter
    @Setter
    public static class SignRequest {
        private String signMessage;
    }
}
