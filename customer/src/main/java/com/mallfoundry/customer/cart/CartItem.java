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

package com.mallfoundry.customer.cart;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@JsonPropertyOrder({"storeId", "productId", "variantId", "quantity", "addedItem"})
@Entity
@Table(name = "customer_cart_item")
public class CartItem implements Serializable {

    @Id
    @Column(name = "id_")
    @GeneratedValue
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cart_id_")
    private Cart cart;

    @Column(name = "store_id_")
    @JsonProperty("store_id")
    private String storeId;

    @Column(name = "product_id_")
    @JsonProperty("product_id")
    private String productId;

    @Column(name = "variant_id_")
    @JsonProperty("variant_id")
    private String variantId;

    @Column(name = "quantity_")
    private Integer quantity;

    @Column(name = "added_time_")
    @JsonProperty("added_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addedItem;

    public CartItem() {
        this.setAddedItem(new Date());
    }

    public CartItem(String storeId, String productId, String variantId, Integer quantity) {
        this();
        this.setStoreId(storeId);
        this.setProductId(productId);
        this.setVariantId(variantId);
        this.setQuantity(quantity);
    }

    public void addQuantity(Integer quantity) {
        this.setQuantity(this.getQuantity() + quantity);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(cart, cartItem.cart) &&
                Objects.equals(variantId, cartItem.variantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cart, variantId);
    }
}
