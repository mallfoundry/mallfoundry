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

package org.mallfoundry.analytics.stream;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.order.OrderItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class TestOrderItem implements OrderItem {
    private String id;
    private String storeId;
    private String productId;
    private String variantId;
    private String imageUrl;
    private String name;
    private List<OptionSelection> optionSelections;
    private int quantity;
    private BigDecimal refundedAmount;
    private BigDecimal refundingAmount;
    private BigDecimal price;
    private BigDecimal discountTotalPrice;
    private BigDecimal shippingCost;
    private BigDecimal discountShippingCost;
    private boolean reviewed;
    private boolean shipped;
    private Date shippedTime;

    public BigDecimal getDiscountTotalPrice() {
        return Objects.requireNonNullElse(this.discountTotalPrice, BigDecimal.ZERO);
    }

    @Override
    public void discountTotalPrice(BigDecimal discountTotalPrice) {

    }

    @Override
    public void applyRefund(BigDecimal refundAmount) {

    }

    @Override
    public void cancelRefund(BigDecimal failAmount) {

    }

    @Override
    public void succeedRefund(BigDecimal succeedAmount) {

    }

    @Override
    public void failRefund(BigDecimal failAmount) {

    }

    @Override
    public void review() {

    }

    @Override
    public void ship() {

    }

    public BigDecimal getShippingCost() {
        return Objects.requireNonNullElse(this.shippingCost, BigDecimal.ZERO);
    }

    public BigDecimal getDiscountShippingCost() {
        return Objects.requireNonNullElse(this.discountShippingCost, BigDecimal.ZERO);
    }

    @Override
    public void discountShippingCost(BigDecimal discountShippingCost) {

    }

    @Override
    public BigDecimal getTotalPrice() {
        return this.getPrice().multiply(BigDecimal.valueOf(this.getQuantity()));
    }

    @Override
    public BigDecimal getSubtotalAmount() {
        return this.getTotalPrice().add(this.getShippingCost());
    }

    @Override
    public BigDecimal getTotalAmount() {
        return this.getSubtotalAmount()
                .add(this.getDiscountTotalPrice())
                .add(this.getDiscountShippingCost());
    }

    @Override
    public Builder toBuilder() {
        return null;
    }
}
