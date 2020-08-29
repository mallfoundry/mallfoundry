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
    private BigDecimal discountAmount;
    private BigDecimal shippingCost;
    private BigDecimal discountShippingCost;
    private boolean reviewed;
    private boolean shipped;
    private Date shippedTime;


    public BigDecimal getDiscountAmount() {
        return Objects.isNull(this.discountAmount)
                ? BigDecimal.ZERO
                : this.discountAmount;
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
        return Objects.isNull(this.shippingCost)
                ? BigDecimal.ZERO
                : this.shippingCost;
    }

    public BigDecimal getDiscountShippingCost() {
        return Objects.isNull(this.discountShippingCost)
                ? BigDecimal.ZERO
                : this.discountShippingCost;
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
                .add(this.getDiscountAmount())
                .add(this.getDiscountShippingCost());
    }

    @Override
    public Builder toBuilder() {
        return null;
    }
}
