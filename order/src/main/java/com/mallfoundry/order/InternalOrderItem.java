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

package com.mallfoundry.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mallfoundry.catalog.OptionSelection;
import com.mallfoundry.catalog.repository.jpa.convert.OptionSelectionListConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "order_items")
public class InternalOrderItem implements OrderItem {

    @Id
    @Column(name = "id_")
    private String id;

    @JsonProperty("store_id")
    @Column(name = "store_id_")
    private String storeId;

    @JsonProperty("product_id")
    @Column(name = "product_id_")
    private String productId;

    @JsonProperty("variant_id")
    @Column(name = "variant_id_")
    private String variantId;

    @JsonProperty("image_url")
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

    @JsonProperty("discount_amount")
    @Column(name = "discount_amount_")
    private BigDecimal discountAmount;

    @JsonProperty("shipping_cost")
    @Column(name = "shipping_cost_")
    private BigDecimal shippingCost;

    @JsonProperty("discount_shipping_cost")
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
        return Objects.isNull(this.discountAmount) ? BigDecimal.ZERO : this.discountAmount;
    }

    public BigDecimal getShippingCost() {
        return Objects.isNull(this.shippingCost) ? BigDecimal.ZERO : this.shippingCost;
    }

    public BigDecimal getDiscountShippingCost() {
        return Objects.isNull(this.discountShippingCost) ? BigDecimal.ZERO : this.discountShippingCost;
    }

    @JsonProperty("subtotal_amount")
    @Transient
    @Override
    public BigDecimal getSubtotalAmount() {
        return this.getPrice().multiply(BigDecimal.valueOf(this.getQuantity()));
    }

    @JsonProperty("original_amount")
    @Transient
    @Override
    public BigDecimal getOriginalAmount() {
        return this.getSubtotalAmount().add(this.getShippingCost());
    }

    @JsonProperty("actual_amount")
    @Override
    public BigDecimal getActualAmount() {
        return this.getSubtotalAmount()
                .add(this.getDiscountAmount())
                .add(this.getShippingCost())
                .add(this.getDiscountShippingCost());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InternalOrderItem that = (InternalOrderItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
