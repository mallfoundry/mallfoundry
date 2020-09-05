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

package org.mallfoundry.order.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.catalog.repository.jpa.convert.OptionSelectionListConverter;
import org.mallfoundry.order.OrderItem;
import org.mallfoundry.order.OrderItemSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_order_item")
public class JpaOrderItem extends OrderItemSupport {

    @NotBlank
    @Id
    @Column(name = "id_")
    private String id;

    @NotBlank
    @Column(name = "store_id_")
    private String storeId;

    @NotBlank
    @Column(name = "product_id_")
    private String productId;

    @NotBlank
    @Column(name = "variant_id_")
    private String variantId;

    @NotBlank
    @Column(name = "image_url_")
    private String imageUrl;

    @NotBlank
    @Column(name = "name_")
    private String name;

    @NotEmpty
    @Column(name = "option_selections_", length = 1024)
    @Convert(converter = OptionSelectionListConverter.class)
    private List<OptionSelection> optionSelections;

    @Min(1)
    @Column(name = "quantity_")
    private int quantity;

    @NotNull
    @Min(0)
    @Column(name = "price_")
    private BigDecimal price;

    @Column(name = "discount_amount_")
    private BigDecimal discountAmount;

    @Column(name = "shipping_cost_")
    private BigDecimal shippingCost;

    @Column(name = "discount_shipping_cost_")
    private BigDecimal discountShippingCost;

    @Column(name = "refunded_amount_")
    private BigDecimal refundedAmount;

    @Column(name = "refunding_amount_")
    private BigDecimal refundingAmount;

    @Column(name = "reviewed_")
    private boolean reviewed;

    @Column(name = "shipped_")
    private boolean shipped;

    @Column(name = "shipped_time_")
    private Date shippedTime;

    public JpaOrderItem(String itemId) {
        this.id = itemId;
    }

    public static JpaOrderItem of(OrderItem item) {
        if (item instanceof JpaOrderItem) {
            return (JpaOrderItem) item;
        }
        var target = new JpaOrderItem();
        BeanUtils.copyProperties(item, target);
        return target;
    }

    @Override
    public BigDecimal getDiscountAmount() {
        return Objects.requireNonNullElse(this.discountAmount, BigDecimal.ZERO);
    }

    @Override
    public BigDecimal getShippingCost() {
        return Objects.requireNonNullElse(this.shippingCost, BigDecimal.ZERO);
    }

    @Override
    public BigDecimal getDiscountShippingCost() {
        return Objects.requireNonNullElse(this.discountShippingCost, BigDecimal.ZERO);
    }

    @Override
    public BigDecimal getRefundedAmount() {
        return Objects.requireNonNullElse(this.refundedAmount, BigDecimal.ZERO);
    }

    @Override
    public BigDecimal getRefundingAmount() {
        return Objects.requireNonNullElse(this.refundingAmount, BigDecimal.ZERO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JpaOrderItem that = (JpaOrderItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
