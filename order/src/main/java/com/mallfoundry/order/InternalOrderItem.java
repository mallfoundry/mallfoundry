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
import com.mallfoundry.data.jpa.convert.StringListConverter;
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

    @Column(name = "title_")
    private String title;

    @JsonProperty("option_values")
    @Column(name = "option_values_")
    @Convert(converter = StringListConverter.class)
    private List<String> optionValues;

    @Column(name = "quantity_")
    private int quantity;

    @Column(name = "price_")
    private BigDecimal price;

    @JsonProperty("discount_amount")
    @Column(name = "discount_amount_")
    private BigDecimal discountAmount = BigDecimal.ZERO;

//    @JsonProperty("shipping_cost")
//    @Column(name = "shipping_cost_")
//    private BigDecimal shippingCost = BigDecimal.ZERO;

    @JsonProperty("subtotal_amount")
    @Transient
    @Override
    public BigDecimal getSubtotalAmount() {
        return this.getPrice().multiply(BigDecimal.valueOf(this.getQuantity()));
    }

    public InternalOrderItem(String productId, String variantId, int quantity) {
        this.setProductId(productId);
        this.setVariantId(variantId);
        this.setQuantity(quantity);
    }

    public static InternalOrderItem of(OrderItem item) {
        if (item instanceof InternalOrderItem) {
            return (InternalOrderItem) item;
        }

        var target = new InternalOrderItem();
        BeanUtils.copyProperties(item, target);
        return target;
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
