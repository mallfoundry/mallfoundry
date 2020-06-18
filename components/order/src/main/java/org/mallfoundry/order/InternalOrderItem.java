/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mallfoundry.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.catalog.product.repository.jpa.convert.OptionSelectionListConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_order_item")
public class InternalOrderItem implements OrderItem {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "store_id_")
    private String storeId;

    @Column(name = "product_id_")
    private String productId;

    @Column(name = "variant_id_")
    private String variantId;

    @Column(name = "image_url_")
    private String imageUrl;

    @Column(name = "name_")
    private String name;

    @Column(name = "option_selections_", length = 1024)
    @Convert(converter = OptionSelectionListConverter.class)
    private List<OptionSelection> optionSelections;

    @Column(name = "quantity_")
    private int quantity;

    @Column(name = "price_")
    private BigDecimal price;

    @Column(name = "discount_amount_")
    private BigDecimal discountAmount;

    @Column(name = "shipping_cost_")
    private BigDecimal shippingCost;

    @Column(name = "discount_shipping_cost_")
    private BigDecimal discountShippingCost;

    public InternalOrderItem(String itemId) {
        this.id = itemId;
    }

    public static InternalOrderItem of(OrderItem item) {
        if (item instanceof InternalOrderItem) {
            return (InternalOrderItem) item;
        }
        var target = new InternalOrderItem();
        BeanUtils.copyProperties(item, target);
        return target;
    }

    public BigDecimal getDiscountAmount() {
        return Objects.isNull(this.discountAmount)
                ? BigDecimal.ZERO
                : this.discountAmount;
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

    @Transient
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InternalOrderItem that = (InternalOrderItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
